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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ru.trfx.dreams.R
import ru.trfx.dreams.core.dream.Dream
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
            containerColor = Color.White,
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
                        fontSize = FontSizes.Normal,
                        style = noPaddingTextStyle,
                    )
                }

                val createdAtText = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(createdAt)
                val createdAtSize = if (title.isNotEmpty()) FontSizes.Tiny else FontSizes.Normal
                Text(
                    text = createdAtText,
                    fontSize = createdAtSize,
                    style = noPaddingTextStyle,
                )
            }

            // Icons
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (lucid) {
                    Icon(
                        painter = painterResource(R.drawable.ic_lucid),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp),
                    )
                }

                if (favorite) {
                    Icon(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(36.dp),
                    )
                }
            }
        }

        // Content
        Spacer(Modifier.height(4.dp))
        Text(
            text = content,
            fontSize = FontSizes.Small,
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
    colorArgb = Color.Black.toArgb(),
    isFavorite = true,
    isLucid = true,
)

private val previewDreamNoTitle = Dream(
    id = 0,
    title = "",
    content = "lorem ipsum dolor sit amet, consectetur adipiscing elit ".repeat(4),
    createdAt = LocalDateTime.of(2025, 5, 11, 8, 21, 11),
    colorArgb = Color.Black.toArgb(),
    isFavorite = true,
    isLucid = true,
)

@Preview
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
