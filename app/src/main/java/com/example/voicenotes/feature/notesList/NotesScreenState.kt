package com.example.voicenotes.feature.notesList

import com.example.voicenotes.domain.models.notes.NoteUi
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

data class NotesScreenState(
    val dialogState: SaveNoteDialogState = SaveNoteDialogState(),
    val recorderState: RecorderState = RecorderState(),
    val notesState: NotesState = NotesState(),
)

data class SaveNoteDialogState(
    val visible: Boolean = false,
    val isError: Boolean = true,
    val name: String = "",
)

data class RecorderState(
    val progress: String = "0:00",
    val recording: Boolean = false,
    val recordButtonScale: Float = 1f,
)

data class NotesState(
    val notes: ImmutableMap<Long, NoteUi> = persistentMapOf(),
    val currentNoteId: Long = 0L,
    val isPlaying: Boolean = false
)