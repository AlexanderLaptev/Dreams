package ru.trfx.dreams.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun DreamsTheme(content: @Composable () -> Unit) {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
    MaterialTheme {
        CompositionLocalProvider(
            LocalColorScheme provides colorScheme,
            LocalContentColor provides colorScheme.text,
            content = content,
        )
    }
}
