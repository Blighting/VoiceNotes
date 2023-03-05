package com.example.voicenotes.feature.notesList

import androidx.lifecycle.viewModelScope
import com.example.voicenotes.core.base.MviViewModel
import com.example.voicenotes.data.repository.NotesRepository
import com.example.voicenotes.domain.models.notes.NoteEntity
import com.example.voicenotes.domain.models.notes.toNoteUI
import com.example.voicenotes.domain.models.player.MusicPlayback
import com.example.voicenotes.domain.usecase.player.PlayerUseCase
import com.example.voicenotes.domain.usecase.record.RecordUseCase
import com.example.voicenotes.domain.utils.convertSecondsToString
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NotesRepository,
    private val recordUseCase: RecordUseCase,
    private val playerUseCase: PlayerUseCase
) : MviViewModel<NotesScreenState, NotesEffect>(initialState = NotesScreenState()) {
    init {
        viewModelScope.launch {
            repository.getNotesFlow().collect(::updateNotesState)
        }
        viewModelScope.launch {
            recordUseCase.volumePlayback.collect(::onVolumeChange)
        }
        viewModelScope.launch {
            recordUseCase.secondsPlayback.collect(::onTimeChange)
        }
        viewModelScope.launch {
            playerUseCase.musicPlayback.collect(::onNotePlayProgress)
        }
    }

    fun onEvent(event: NotesScreenEvent) {
        when (event) {
            is SaveDialogEvent -> onSaveDialogEvent(event)
            is RecorderEvent -> onRecorderEvent(event)
            is NoteEvent -> onNoteEvent(event)
        }
    }


    private fun onSaveDialogEvent(event: SaveDialogEvent) {
        when (event) {
            SaveDialogEvent.Cancel -> onDialogCancel()
            SaveDialogEvent.Save -> onDialogSave()
            is SaveDialogEvent.ValueChange -> onDialogValueChange(event.value)
        }
    }

    private fun onRecorderEvent(event: RecorderEvent) {
        when (event) {
            RecorderEvent.ChangeStatus -> onRecordingStateChange()
            RecorderEvent.Cancel -> onRecordStop()
        }
    }

    private fun onNoteEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Click -> onNoteClick(event.id)
        }
    }

    private fun onDialogCancel() {
        resetDialogState()
    }

    private fun onDialogSave() {
        viewModelScope.launch {
            recordUseCase.save(state.value.dialogState.name)
            resetDialogState()
        }
    }

    private fun onDialogValueChange(value: String) {
        fun String.isSaveDialogValueCorrect() = this.isBlank()
        setState {
            copy(
                dialogState = this.dialogState.copy(
                    name = value,
                    isError = value.isSaveDialogValueCorrect()
                )
            )
        }
    }

    private fun onRecordingStateChange() {
        if (state.value.recorderState.recording) {
            stopRecording()
            setState {
                copy(
                    dialogState = this.dialogState.copy(
                        visible = true
                    ),
                    recorderState = RecorderState()
                )
            }
        } else {
            startRecording()
            setState {
                copy(
                    recorderState = this.recorderState.copy(
                        recording = true,
                    )
                )
            }
        }
    }

    private fun onRecordStop() {
        stopRecording()
        setState {
            copy(
                recorderState = RecorderState()
            )
        }
    }

    private fun onNoteClick(id: Long) {
        if (state.value.notesState.isPlaying) {
            stopPlayingNote(id)
        } else playNote(id)
    }

    private fun playNote(id: Long) {
        viewModelScope.launch {
            val note = state.value.notesState.notes[id]!!
            val success = if (note.progressAsString == null) {
                playerUseCase.startPlay(id)
            } else if (state.value.notesState.currentNoteId != id) {
                playerUseCase.startPlayWithProgress(id, note.progress)
            } else {
                playerUseCase.play()
                true
            }
            if (!success) {
                playTrackFail(id)
            } else {
                val notes = state.value.notesState.notes.toMutableMap()
                notes[id] = notes[id]!!.copy(
                    isPlaying = true
                )
                setState {
                    copy(
                        notesState = notesState.copy(
                            isPlaying = true,
                            currentNoteId = id,
                            notes = notes.toImmutableMap()
                        )
                    )
                }
            }
        }
    }

    private suspend fun playTrackFail(id: Long) {
        repository.deleteNote(id)
        postEffect(NotesEffect.NoSuchTrack)
    }

    private fun stopPlayingNote(id: Long) {
        playerUseCase.pause()
        val notes = state.value.notesState.notes.toMutableMap()
        notes[id] = notes[id]!!.copy(
            isPlaying = false
        )
        setState {
            copy(
                notesState = notesState.copy(
                    isPlaying = false,
                    notes = notes.toImmutableMap()
                )
            )
        }
    }

    private fun onNotePlayProgress(playback: MusicPlayback) {
        if (state.value.notesState.isPlaying) {
            val notesState = state.value.notesState
            val notes = notesState.notes.toMutableMap()
            val id = notesState.currentNoteId
            notes[id] = notes[id]!!.copy(
                progress = playback.progress,
                progressAsString = playback.currentTime
            )
            setState {
                copy(
                    notesState = notesState.copy(
                        notes = notes.toImmutableMap()
                    )
                )
            }
        }
    }

    private fun onVolumeChange(volume: Int) {
        val scale = 1f + (volume / 32767f * 0.8).toFloat()
        setState {
            copy(
                recorderState = recorderState.copy(
                    recordButtonScale = scale,
                )
            )
        }
    }

    private fun onTimeChange(time: Int) {
        setState {
            copy(
                recorderState = recorderState.copy(
                    progress = time.convertSecondsToString()
                )
            )
        }
    }

    private fun startRecording() {
        recordUseCase.start()
    }

    private fun stopRecording() {
        recordUseCase.stop()
    }

    private fun updateNotesState(list: List<NoteEntity>) {
        val notes = list.associateBy({ it.id }, { it.toNoteUI() }).toImmutableMap()
        setState { copy(notesState = this.notesState.copy(notes = notes)) }
    }

    private fun resetDialogState() {
        setState { copy(dialogState = SaveNoteDialogState()) }
    }
}