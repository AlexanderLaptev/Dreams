package ru.trfx.dreams.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

fun pickContrastingColor(background: Color, first: Color, second: Color): Color {
    val firstContrast = ColorUtils.calculateContrast(first.toArgb(), background.toArgb())
    val secondContrast = ColorUtils.calculateContrast(second.toArgb(), background.toArgb())
    return if (firstContrast >= secondContrast) first else second
}
