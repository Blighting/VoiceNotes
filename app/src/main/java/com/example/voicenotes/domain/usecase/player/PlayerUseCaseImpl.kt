package com.example.voicenotes.domain.usecase.player

import android.content.Context
import android.database.Cursor
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SeekParameters
import com.example.voicenotes.data.repository.NotesRepository
import com.example.voicenotes.domain.models.player.MusicPlayback
import com.example.voicenotes.domain.utils.convertMillisecondsToString
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter

private const val TRACK_PROGRESS_REFRESH_RATE_MILLISECONDS = 100L
private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

class PlayerUseCaseImpl(
    private val player: ExoPlayer,
    private val repository: NotesRepository,
    private val context: Context
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

    override suspend fun startPlay(id: Long): Boolean {
        val note = repository.getNote(id)
        val uri = note.uri
        if(!ensureMediaItemCorrect(uri)) return false
        val track: MediaItem = MediaItem.fromUri(uri)
        player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        player.setMediaItem(track)
        player.prepare()
        player.play()
        return true
    }

    //For some reason this doesnt work
    override suspend fun startPlayWithProgress(id: Long, progress: Float): Boolean {
        val note = repository.getNote(id)
        val uri = note.uri
        if(!ensureMediaItemCorrect(uri)) return false
        val track = MediaItem.fromUri(uri)
        val targetTime = (note.duration * progress).toLong()
        player.setMediaItem(track,targetTime)
        player.prepare()
        player.play()
        return true
    }

    private fun ensureMediaItemCorrect(uri: String): Boolean {
        val resolver = context.contentResolver
        var cursor: Cursor? = null
        val isUriExist: Boolean = try {
            cursor = resolver.query(uri.toUri(), null, null, null, null)
            //cursor null: content Uri was invalid or some other error occurred
            //cursor.moveToFirst() false: Uri was ok but no entry found.
            (cursor != null && cursor.moveToFirst())
        } catch (t: Throwable) {
            false
        } finally {
            try {
                cursor?.close()
            } catch (t: Throwable) {
            }
        }
        return isUriExist
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
