package com.example.voicenotes.core.di

import androidx.media3.exoplayer.ExoPlayer
import com.example.voicenotes.data.database.NotesDao
import com.example.voicenotes.data.repository.NotesRepository
import com.example.voicenotes.domain.usecase.notes.SaveNoteUseCase
import com.example.voicenotes.domain.usecase.notes.SaveNoteUseCaseImpl
import com.example.voicenotes.domain.usecase.player.PlayerUseCase
import com.example.voicenotes.domain.usecase.player.PlayerUseCaseImpl
import com.example.voicenotes.domain.usecase.record.RecordUseCase
import com.example.voicenotes.domain.usecase.record.RecordUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val useCaseModule = module {

    single<SaveNoteUseCase> {
        SaveNoteUseCaseImpl(
            dao = get<NotesDao>(),
            context = androidContext()
        )
    }

    single<RecordUseCase> {
        RecordUseCaseImpl(
//            audioRecordProvider = get(),
            context = androidContext(),
            saveNoteUseCase = get(),
        )
    }

    single {
        ExoPlayer.Builder(androidContext()).build()
    }

    single<PlayerUseCase> {
        PlayerUseCaseImpl(
            player = get<ExoPlayer>(),
            repository = get<NotesRepository>(),
        )
    }
}