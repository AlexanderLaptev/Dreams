package ru.trfx.dreams.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ru.trfx.dreams.R
import ru.trfx.dreams.core.dream.Dream
import ru.trfx.dreams.ui.theme.CurrentColorScheme
import ru.trfx.dreams.ui.theme.DreamsTheme
import ru.trfx.dreams.ui.theme.FontSizes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val cardShape = RoundedCornerShape(12.dp)
private val stripShape = RoundedCornerShape(100)

private val noPaddingTextStyle = TextStyle(
    platformStyle = PlatformTextStyle(includeFontPadding = false),
)

@Composable
fun DreamCard(
    dream: Dream,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = cardShape,
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = CurrentColorScheme.container,
        ),
    ) {
        ConstraintLayout(modifier = Modifier.padding(16.dp)) {
            val strip = createRef()
            val content = createRef()

            val stripColor = Color(dream.colorArgb)
            Spacer(
                modifier = Modifier
                    .clip(stripShape)
                    .background(color = stripColor)
                    .constrainAs(strip) {
                        start.linkTo(parent.start)
                        top.linkTo(content.top)
                        bottom.linkTo(content.bottom)

                        width = Dimension.value(6.dp)
                        height = Dimension.fillToConstraints
                    },
            )

            CardContents(
                title = dream.title,
                content = dream.content,
                favorite = dream.isFavorite,
                lucid = dream.isLucid,
                createdAt = dream.createdAt,
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(strip.end, margin = 8.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)

                        width = Dimension.preferredWrapContent
                        height = Dimension.preferredWrapContent
                    },
            )
        }
    }
}

@Composable
private fun CardContents(
    title: String,
    content: String,
    favorite: Boolean,
    lucid: Boolean,
    createdAt: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Title & date/time
            Column(Modifier.weight(1.0f)) {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        fontSize = FontSizes.normal,
                        style = noPaddingTextStyle,
                    )
                }

                val pattern = stringResource(R.string.pattern_created_at)
                val formatter = remember { DateTimeFormatter.ofPattern(pattern) }
                val createdAtSize = if (title.isNotEmpty()) FontSizes.tiny else FontSizes.normal
                Text(
                    text = formatter.format(createdAt),
                    fontSize = createdAtSize,
                    color = CurrentColorScheme.subtext,
                    style = noPaddingTextStyle,
                )
            }

            // Icons
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (lucid) {
                    Icon(
                        painter = painterResource(R.drawable.ic_lucid),
                        contentDescription = null,
                        tint = CurrentColorScheme.lucid,
                        modifier = Modifier.size(32.dp),
                    )
                }

                if (favorite) {
                    Icon(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = null,
                        tint = CurrentColorScheme.favorite,
                        modifier = Modifier.size(36.dp),
                    )
                }
            }
        }

        // Content
        Spacer(Modifier.height(4.dp))
        Text(
            text = content,
            fontSize = FontSizes.small,
            lineHeight = (1.1).em,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
        )
    }
}

private val previewDreamTitle = Dream(
    id = 0,
    title = "Lorem ipsum dolor sit",
    content = "lorem ipsum dolor sit amet, consectetur adipiscing elit ".repeat(4),
    createdAt = LocalDateTime.of(2025, 5, 11, 8, 21, 11),
    colorArgb = Color.Gray.toArgb(),
    isFavorite = true,
    isLucid = true,
)

private val previewDreamNoTitle = Dream(
    id = 0,
    title = "",
    content = "lorem ipsum dolor sit amet, consectetur adipiscing elit ".repeat(4),
    createdAt = LocalDateTime.of(2025, 5, 11, 8, 21, 11),
    colorArgb = Color.Gray.toArgb(),
    isFavorite = true,
    isLucid = true,
)

@PreviewLightDark
@Composable
private fun Preview() {
    DreamsTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DreamCard(
                dream = previewDreamTitle,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
            DreamCard(
                dream = previewDreamNoTitle,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
