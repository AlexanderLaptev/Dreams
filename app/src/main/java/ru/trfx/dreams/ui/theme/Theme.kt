package ru.trfx.dreams.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun DreamsTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        CompositionLocalProvider(
            LocalContentColor provides Color.Black,
            content = content,
        )
    }
}
