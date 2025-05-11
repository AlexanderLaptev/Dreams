package ru.trfx.dreams.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.trfx.dreams.ui.theme.DreamsTheme
import ru.trfx.dreams.ui.util.pickContrastingColor

private val tagShape = RoundedCornerShape(8.dp)
private val lightTextColor = Color.White
private val darkTextColor = Color.Black

@Composable
fun TagChip(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .clip(tagShape)
            .background(color = color, shape = tagShape)
            .defaultMinSize(minHeight = 24.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        val textColor = pickContrastingColor(color, lightTextColor, darkTextColor)
        Text(
            text = text,
            color = textColor,
        )
    }
}

@Preview
@Composable
private fun PreviewDisplay() {
    DreamsTheme {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            TagChip(
                text = "\uD83D\uDE00 lorem ipsum",
                color = Color.White,
            )
            TagChip(
                text = "\uD83D\uDE00 lorem ipsum",
                color = Color.White,
            )
        }
    }
}
