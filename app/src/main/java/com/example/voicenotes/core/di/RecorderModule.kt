package com.example.voicenotes.core.di

import com.example.voicenotes.domain.usecase.record.AudioRecordProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val recorderModule = module {
//    single { AudioRecordProvider(androidContext()) }
}