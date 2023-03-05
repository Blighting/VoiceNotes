package com.example.voicenotes.domain.usecase.player

import com.example.voicenotes.domain.models.player.MusicPlayback
import kotlinx.coroutines.flow.Flow

interface PlayerUseCase {
    val musicPlayback: Flow<MusicPlayback>
    suspend fun startPlay(id: Long): Boolean
    suspend fun startPlayWithProgress(id: Long, progress: Float): Boolean
    fun play()
    fun pause()
}