package com.example.voicenotes.feature.notesList.ui

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay

@Preview
@Composable
fun PreviewWaveAnimation() {
    val kekw = remember { mutableStateOf(true) }
    Surface(
        color = Color.Black
    ) {
        RecordButtonWithAnimation(
            isRecording = kekw.value,
            onClick = { kekw.value = !kekw.value },
            scale = 1.0f,
        )
    }
}

@Composable
fun RecordButtonWithAnimation(
    scale: Float,
    isRecording: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val wave = remember(isRecording) { Animatable(0f) }
    val scaleState by animateFloatAsState(targetValue = scale)
    LaunchedEffect(isRecording) {
        delay(1000L)
        wave.animateTo(
            targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000,
                    easing = FastOutLinearInEasing
                ),
                repeatMode = RepeatMode.Restart,
            )
        )
    }
    // Wave
    if (isRecording) {
        Box(
            modifier
                .size(48.dp)
                .scale(scale = scaleState)
                .graphicsLayer {
                    scaleX = wave.value / 4 + 1
                    scaleY = wave.value / 4 + 1
                    alpha = 1 - wave.value
                },
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.Red, shape = CircleShape),
            )
        }
    }
    RecordButton(
        modifier = modifier,
        isRecording = isRecording,
        onClick = onClick,
    )
}

