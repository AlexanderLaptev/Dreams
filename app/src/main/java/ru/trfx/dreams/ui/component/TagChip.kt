package ru.trfx.dreams.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.trfx.dreams.core.tag.Tag
import ru.trfx.dreams.ui.theme.DreamsTheme

private val tagShape = RoundedCornerShape(8.dp)

@Composable
fun TagChip(
    tag: Tag,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .clip(tagShape)
            .background(color = Color(tag.colorArgb), shape = tagShape)
            .defaultMinSize(minHeight = 24.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = tag.name,
        )
    }
}

private val previewTag = Tag(
    id = 0,
    name = "lorem ipsum",
    colorArgb = Color.White.toArgb(),
)

@Preview
@Composable
private fun PreviewDisplay() {
    DreamsTheme {
        TagChip(tag = previewTag)
    }
}

@Preview
@Composable
private fun PreviewClickable() {
    DreamsTheme {
        TagChip(
            tag = previewTag,
            onClick = {},
        )
    }
}
