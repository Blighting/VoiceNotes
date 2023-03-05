package com.example.voicenotes.domain.usecase.player

import com.example.voicenotes.domain.models.player.MusicPlayback
import kotlinx.coroutines.flow.Flow

interface PlayerUseCase {
    val musicPlayback: Flow<MusicPlayback>
    fun startPlay(id: Long)
    fun startPlayWithProgress(id: Long, progress: Float)
    fun play()
    fun pause()
}