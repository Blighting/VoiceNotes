package com.example.voicenotes.core

import android.app.Application
import com.example.voicenotes.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                databaseModule,
                useCaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}