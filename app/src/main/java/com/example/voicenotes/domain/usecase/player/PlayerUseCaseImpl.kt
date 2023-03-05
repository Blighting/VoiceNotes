package com.example.voicenotes.domain.usecase.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.voicenotes.data.repository.NotesRepository
import com.example.voicenotes.domain.models.player.MusicPlayback
import com.example.voicenotes.domain.utils.convertMillisecondsToString
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter

private const val TRACK_PROGRESS_REFRESH_RATE_MILLISECONDS = 100L
private val scope = CoroutineScope(Dispatchers.Main)

class PlayerUseCaseImpl(
    private val player: ExoPlayer,
    private val repository: NotesRepository,
) : PlayerUseCase {
    private val _musicPlayback = MutableSharedFlow<MusicPlayback>()
    override val musicPlayback: Flow<MusicPlayback> = _musicPlayback
    private val isPlayingFlow = MutableStateFlow(false)

    init {
        player.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    isPlayingFlow.value = isPlaying
                }
            }
        )
        scope.launch {
            isPlayingFlow.filter { it }.collect {
                while (isPlayingFlow.value) {
                    delay(TRACK_PROGRESS_REFRESH_RATE_MILLISECONDS)
                    _musicPlayback.emit(
                        MusicPlayback(
                            progress = player.getProgress(),
                            currentTime = player.currentPosition.convertMillisecondsToString()
                        )
                    )
                }
            }
        }
    }

    override fun startPlay(id: Long) {
        var track: MediaItem
        scope.launch {
            withContext(Dispatchers.Default) {
                track = MediaItem.fromUri(repository.getNote(id).uri)
            }
            player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
            player.setMediaItem(track)
            player.prepare()
            player.play()
        }
    }

    override fun startPlayWithProgress(id: Long, progress: Float) {
        scope.launch {
            var track: MediaItem
            var targetTime: Long
            withContext(Dispatchers.Default) {
                val note = repository.getNote(id)
                track = MediaItem.fromUri(note.uri)
                targetTime = (note.duration * progress).toLong()
            }
            player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
            player.setMediaItem(track)
            player.seekTo(targetTime)
            player.prepare()
            player.play()
        }
    }

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }


    private fun ExoPlayer.getProgress(): Float {
        val duration = this.duration.toFloat()
        val currentTime = this.currentPosition.toFloat()
        return (currentTime / duration)
    }
}