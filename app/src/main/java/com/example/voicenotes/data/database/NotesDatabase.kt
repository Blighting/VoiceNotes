package com.example.voicenotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.voicenotes.domain.models.notes.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}