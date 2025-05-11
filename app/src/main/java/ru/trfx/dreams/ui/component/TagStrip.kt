package ru.trfx.dreams.ui.component

import androidx.collection.mutableIntListOf
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.trfx.dreams.core.tag.Tag
import ru.trfx.dreams.ui.theme.DreamsTheme
import kotlin.math.max

@Composable
fun TagStrip(
    tags: List<Tag>,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    spacing: Dp = 4.dp,
) {
    Layout(
        contents = listOf(
            { for (tag in tags) TagChip(tag.name, Color(tag.colorArgb)) },
            { TagChip("...", Color.White) }
        ),
        modifier = modifier.clipToBounds(),
    ) { measurables, constraints ->
        val tagPlaceables = measurables[0].map { it.measure(constraints) }
        val overflowPlaceable = measurables[1][0].measure(constraints)
        val spacingPx = spacing.roundToPx()

        var totalWidth = 0
        var totalHeight = 0
        val lineBreaks = mutableIntListOf()
        val lineHeights = mutableIntListOf()
        var count = 0
        var hasOverflown = false

        var lineWidth = 0
        var lineHeight = 0
        var lineIndex = 0
        var lineElementCount = 0

        for (i in tagPlaceables.indices) {
            val p = tagPlaceables[i]
            var newLineWidth = lineWidth + p.width
            if (lineWidth != 0) newLineWidth += spacingPx

            if (newLineWidth > constraints.maxWidth) {
                if (lineIndex < maxLines - 1) { // more lines -> line break
                    totalHeight += lineHeight + spacingPx
                    lineBreaks += i - 1
                    lineHeights += lineHeight

                    lineWidth = p.width
                    lineHeight = p.height
                    lineElementCount = 1
                    lineIndex++
                    count++
                } else { // last line -> overflow
                    hasOverflown = true
                    newLineWidth = lineWidth + overflowPlaceable.width

                    var backtrackIndex = 0
                    while (newLineWidth > constraints.maxWidth) {
                        if (lineElementCount == 0) break
                        newLineWidth -= tagPlaceables[i - backtrackIndex].width
                        lineElementCount--
                        count--
                        backtrackIndex++
                    }
                    break
                }
            } else {
                lineWidth = newLineWidth
                lineHeight = max(lineHeight, p.height)
                totalWidth = max(totalWidth, lineWidth)
                lineElementCount++
                count++
            }
        }
        lineHeights += lineHeight
        totalHeight += lineHeight

        layout(totalWidth, totalHeight) {
            var posX = 0
            var posY = 0
            @Suppress("NAME_SHADOWING") var lineIndex = 0

            for (i in 0..<count) {
                val p = tagPlaceables[i]

                if (posX != 0) posX += spacingPx
                p.placeRelative(posX, posY)
                posX += p.width

                if (lineBreaks.isNotEmpty() && i == lineBreaks[0]) {
                    lineBreaks.removeAt(0)
                    posX = 0
                    posY += lineHeights[lineIndex] + spacingPx
                    lineIndex++
                }
            }

            if (hasOverflown) {
                if (posX != 0) posX += spacingPx
                overflowPlaceable.placeRelative(posX, posY)
            }
        }
    }
}

private val previewTags = listOf(
//    Tag(id = 0, name = "lorem ipsum dolor sit amet consectetur adipiscing elit lorem ipsum 1", Color.White.toArgb()),
    Tag(id = 0, name = "lorem ipsum 1", Color.White.toArgb()),
    Tag(id = 0, name = "ipsum 2", Color.White.toArgb()),
    Tag(id = 0, name = "lorem ipsum 3", Color.White.toArgb()),
    Tag(id = 0, name = "lorem ipsum 4", Color.White.toArgb()),
    Tag(id = 0, name = "lorem 5", Color.White.toArgb()),
    Tag(id = 0, name = "abc 6", Color.White.toArgb()),
    Tag(id = 0, name = "lorem ipsum 7", Color.White.toArgb()),
)

@Preview
@Composable
private fun Preview() {
    DreamsTheme {
        TagStrip(previewTags, maxLines = 2)
    }
}
