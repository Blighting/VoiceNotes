package com.example.voicenotes.domain.models.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.voicenotes.domain.utils.convertMillisecondsToString
import com.example.voicenotes.domain.utils.millisecondsToSimpleDate

@Entity(tableName = "note_entity")
data class NoteEntity(
    val name: String,
    val timestamp: Long,
    val uri: String,
    val duration: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

fun NoteEntity.toNoteUI() =
    NoteUi(
        name = name,
        id = id,
        date = timestamp.millisecondsToSimpleDate(),
        duration = this.duration.convertMillisecondsToString(),
        progress = 0f,
        progressAsString = null,
        isPlaying = false,
    )
