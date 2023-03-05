package com.example.voicenotes.domain.usecase.notes

interface SaveNoteUseCase {
    suspend fun saveNote(name: String, oldPath: String)
}