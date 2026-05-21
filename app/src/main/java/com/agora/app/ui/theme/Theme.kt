package com.agora.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AgoraColorScheme = lightColorScheme(
    primary = Color(0xFF1A6B8A),
    secondary = Color(0xFF54E6D4),
    background = Color(0xFFF1F7F6),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun AgoraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AgoraColorScheme,
        typography = Typography,
        content = content
    )
}