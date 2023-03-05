package com.example.voicenotes.feature.notesList.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voicenotes.R
import com.example.voicenotes.core.theme.VoiceNotesTheme
import com.example.voicenotes.domain.models.notes.NoteUi

@Preview
@Composable
private fun NotePreview() {
    Note(
        model = NoteUi(
            name = "Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату ",
            date = "Сегодня в 12:51",
            progressAsString = "5:32",
            isPlaying = false,
            progress = 0.4f,
            duration = "",
            id = 0L
        ),
        onPlay = {},
    )
}

@Composable
fun Note(
    model: NoteUi,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(VoiceNotesTheme.colors.cardBackground),
    ) {
        Row(
            modifier = modifier
                .padding(start = 16.dp, top = 6.dp, bottom = 6.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.name,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    ),
                    maxLines = 1,
                )
                Text(
                    modifier = Modifier,
                    text = model.date,
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                    color = Color.Gray,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = model.progressWithDuration,
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                    color = Color.Gray,
                )
                PlayButton(
                    modifier = Modifier.padding(start = 16.dp),
                    isTrackPlaying = model.isPlaying,
                    onPlay = onPlay,
                )
            }
        }
        NoteProgress(model.progress)
    }
}

@Composable
private fun PlayButton(
    isTrackPlaying: Boolean,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = if (isTrackPlaying) VoiceNotesTheme.colors.buttonBackground
        else VoiceNotesTheme.colors.primary
    IconButton(
        onClick = onPlay,
        modifier = modifier
            .clip(CircleShape)
            .background(color),
    ) {
        Icon(
            painter = if (isTrackPlaying) painterResource(id = R.drawable.ic_pause)
            else painterResource(id = R.drawable.ic_baseline_play_arrow_24),
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@Composable
private fun BoxScope.NoteProgress(progress: Float) {
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
            .align(Alignment.BottomCenter),
        progress = animatedProgress,
        color = VoiceNotesTheme.colors.primary,
        backgroundColor = Color.Transparent,
    )
}


