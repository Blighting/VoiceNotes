package com.example.voicenotes.core.di

import com.example.voicenotes.data.repository.NotesRepository
import com.example.voicenotes.domain.usecase.player.PlayerUseCase
import com.example.voicenotes.domain.usecase.record.RecordUseCase
import com.example.voicenotes.feature.notesList.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NotesViewModel(
            repository = get<NotesRepository>(),
            recordUseCase = get<RecordUseCase>(),
            playerUseCase = get<PlayerUseCase>(),
        )
    }
}