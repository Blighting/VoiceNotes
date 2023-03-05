package com.example.voicenotes.core.di

import com.example.voicenotes.data.repository.NotesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { NotesRepository(notesDao = get()) }
}