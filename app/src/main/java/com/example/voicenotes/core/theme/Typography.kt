package com.globus.trullo.android.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class TrulloTypography(
    val h1: TextStyle,
)

private val Typography = TrulloTypography(
    h1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
)

val LocalTypography = staticCompositionLocalOf { Typography }