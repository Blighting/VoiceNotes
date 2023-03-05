package com.example.voicenotes.feature.notesList.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.voicenotes.core.theme.VoiceNotesTheme

@Composable
fun RecordButton(
    onClick: () -> Unit,
    isRecording: Boolean,
    modifier: Modifier = Modifier,
) {
    val icon = if(isRecording) Icons.Filled.Send else Icons.Filled.Mic
    IconButton(
        modifier = modifier
            .clip(CircleShape)
            .background(VoiceNotesTheme.colors.primary),
        onClick = onClick,
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
    }
}