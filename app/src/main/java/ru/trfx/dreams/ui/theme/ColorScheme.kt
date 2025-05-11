package ru.trfx.dreams.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val container: Color,
    val text: Color,
    val subtext: Color,
    val lucid: Color,
    val favorite: Color,
)

val LightColorScheme = ColorScheme(
    container = Color.White,
    text = Color.Black,
    subtext = Color.DarkGray,
    lucid = Color(0xFFB752F5),
    favorite = Color(0xFFD98900),
)

val DarkColorScheme = ColorScheme(
    container = Color.Black,
    text = Color.White,
    subtext = Color.LightGray,
    lucid = Color(0xFFD083FF),
    favorite = Color(0xFFFFA200),
)

val LocalColorScheme = compositionLocalOf { LightColorScheme }

val CurrentColorScheme: ColorScheme
    @Composable get() = LocalColorScheme.current
