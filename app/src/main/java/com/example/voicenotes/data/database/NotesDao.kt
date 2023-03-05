package com.example.voicenotes.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.voicenotes.domain.models.notes.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNote(track: NoteEntity)

    @Query("SELECT * FROM note_entity")
    abstract fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_entity WHERE id=:id")
    abstract fun getNote(id: Long): NoteEntity

    @Query("DELETE FROM note_entity WHERE id=:id")
    abstract fun deleteNote(id: Long)
}