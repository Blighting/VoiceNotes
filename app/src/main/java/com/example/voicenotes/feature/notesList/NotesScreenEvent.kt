package com.example.voicenotes.feature.notesList

sealed class NotesScreenEvent

sealed class SaveDialogEvent : NotesScreenEvent() {
    object Save : SaveDialogEvent()
    object Cancel : SaveDialogEvent()
    data class ValueChange(val value: String) : SaveDialogEvent()
}

sealed class RecorderEvent : NotesScreenEvent() {
    object ChangeStatus : RecorderEvent()
    object Cancel : RecorderEvent()
}

sealed class NoteEvent : NotesScreenEvent() {
    data class Click(val id: Long) : NoteEvent()
}