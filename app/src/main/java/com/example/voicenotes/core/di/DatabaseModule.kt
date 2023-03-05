package com.example.voicenotes.core.di

import androidx.room.Room
import com.example.voicenotes.BuildConfig
import com.example.voicenotes.data.database.NotesDao
import com.example.voicenotes.data.database.NotesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NotesDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
    single<NotesDao> { get<NotesDatabase>().getNotesDao() }
}