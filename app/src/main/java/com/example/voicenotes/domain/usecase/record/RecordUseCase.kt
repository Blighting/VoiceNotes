package com.example.voicenotes.domain.usecase.record

import kotlinx.coroutines.flow.Flow

interface RecordUseCase {
    fun start()
    fun stop()
    suspend fun save(name: String)
    val volumePlayback: Flow<Int>
    val secondsPlayback: Flow<Int>
}