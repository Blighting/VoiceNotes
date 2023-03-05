package com.example.voicenotes.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class VoiceNotesColors(
    val primary: Color,
    val error: Color,
    val text: Color,
    val background: Color,
    val primaryText: Color,
    val cardBackground: Color,
)

private val Colors = VoiceNotesColors(
    primary = Color(0xFF0077FF),
    error = Color.Red,
    text = Color.Black,
    background = Color.White,
    primaryText = Color.Black,
    cardBackground = Color(0xFFf5f5f5)
)

val LocalColors = staticCompositionLocalOf { Colors }
