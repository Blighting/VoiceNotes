package com.example.voicenotes.data.repository

import com.example.voicenotes.data.database.NotesDao

class NotesRepository(
    private val notesDao: NotesDao
) {
    fun getNotesFlow() = notesDao.getNotes()

    suspend fun getNote(id: Long) = notesDao.getNote(id)
}