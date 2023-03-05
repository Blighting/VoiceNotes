package com.example.voicenotes.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.globus.trullo.android.ui.theme.LocalTypography
import com.globus.trullo.android.ui.theme.TrulloTypography

object VoiceNotesTheme {
    val colors: VoiceNotesColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val shapes: VoiceNotesShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val typography: TrulloTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun VoiceNotesTheme(
    colors: VoiceNotesColors = VoiceNotesTheme.colors,
    shapes: VoiceNotesShapes = VoiceNotesTheme.shapes,
    typography: TrulloTypography = VoiceNotesTheme.typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShapes provides shapes,
        LocalTypography provides typography,
        content = content,
    )
}