package com.example.voicenotes.domain.usecase.record

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import com.example.voicenotes.domain.usecase.notes.SaveNoteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

private const val RECORD_VOLUME_REFRESH_RATE_MILLISECONDS = 100L
private const val RECORD_SECONDS_REFRESH_RATE_MILLISECONDS = 1000L
private val scope = CoroutineScope(Dispatchers.Default)

class RecordUseCaseImpl(
//    private val audioRecordProvider: AudioRecordProvider,
    private val context: Context,
    private val saveNoteUseCase: SaveNoteUseCase,
) : RecordUseCase {
    private val audioRecordProvider: AudioRecordProvider by lazy {
        Log.d("LOl", "create audio record provider")
        AudioRecordProvider(context)
    }
    private val path by lazy { audioRecordProvider.filesDirPath }
    private val tempName by lazy { "$path/temp.mp3" }
    private var mediaRecorder: MediaRecorder? = null
    private val isPlayingFlow = MutableStateFlow(false)
    private val _volumePlayback = MutableSharedFlow<Int>()
    override val volumePlayback: Flow<Int> = _volumePlayback
    private val _secondsPlayback = MutableStateFlow(0)
    override val secondsPlayback: Flow<Int> = _secondsPlayback

    init {
        scope.launch {
            isPlayingFlow.filter { it }.collect {
                while (isPlayingFlow.value && mediaRecorder != null) {
                    delay(RECORD_VOLUME_REFRESH_RATE_MILLISECONDS)
                    _volumePlayback.emit(
                        mediaRecorder!!.maxAmplitude
                    )
                }
            }
        }
        scope.launch {
            isPlayingFlow.filter { it }.collect {
                while (isPlayingFlow.value) {
                    delay(RECORD_SECONDS_REFRESH_RATE_MILLISECONDS)
                    val secondsValue = (_secondsPlayback.value + 1)
                    _secondsPlayback.emit(secondsValue)
                }
            }
        }
    }


    override fun start() {
        scope.launch {
            _secondsPlayback.emit(0)
        }
        mediaRecorder = audioRecordProvider.provideAudioRecord(tempName)
        mediaRecorder?.start()
        isPlayingFlow.value = true
    }

    override fun stop() {
        mediaRecorder?.stop()
        mediaRecorder!!.reset()
        isPlayingFlow.value = false
    }

    override suspend fun save(name: String) {
        saveNoteUseCase.saveNote(name, tempName)
    }
}