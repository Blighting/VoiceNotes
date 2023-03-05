package com.example.voicenotes.data.repository

import com.example.voicenotes.data.database.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRepository(
    private val notesDao: NotesDao
) {
    suspend fun getNotesFlow() = withContext(Dispatchers.IO) { notesDao.getNotes() }
    suspend fun getNote(id: Long) = withContext(Dispatchers.IO) { notesDao.getNote(id) }
    suspend fun deleteNote(id: Long) = withContext(Dispatchers.IO) { notesDao.deleteNote(id) }
}