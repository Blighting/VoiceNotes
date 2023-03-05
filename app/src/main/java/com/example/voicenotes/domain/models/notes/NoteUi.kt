package com.example.voicenotes.domain.models.notes

data class NoteUi(
    val name: String,
    val date: String,
    val progressAsString: String? = null,
    val id: Long,
    val duration: String,
    val progress: Float,
    val isPlaying: Boolean,
){
    val progressWithDuration: String
        get() = if (progressAsString != null) "$progressAsString/$duration" else "$duration"
}
