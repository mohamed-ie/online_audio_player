package com.example.onlineaudioplayer.glance.view.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceComposable
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

@Composable
@GlanceComposable
fun ChannelCard(
    channel: RadioChannel,
    onClick: () -> Unit,
    onFavorite: () -> Unit,
    isRtl: Boolean = false
) = if (isRtl)
    ChannelCardRtl(channel = channel, onClick = onClick, onFavorite = onFavorite)
else ChannelCardLtr(channel = channel, onClick = onClick, onFavorite = onFavorite)


@Composable
@GlanceComposable
private fun ChannelCardLtr(
    channel: RadioChannel,
    onClick: () -> Unit,
    onFavorite: () -> Unit
) = with(channel) {
    val context = LocalContext.current
    Column(modifier = GlanceModifier.clickable(block = onClick)) {
        Row(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Column(modifier = GlanceModifier.defaultWeight()) {
                Text(text = name, maxLines = 1, style = MaterialTheme.typography.titleMedium.run {
                    TextStyle(
                        fontWeight = FontWeight.Medium, fontSize = fontSize
                    )
                })

                Spacer(modifier = GlanceModifier.height(2.dp))
                Text(text = description.ifBlank { context.getString(R.string.no_description) },
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.run {
                        TextStyle(
                            fontWeight = FontWeight.Normal,
                            color = GlanceTheme.colors.outline,
                            fontSize = fontSize
                        )
                    })
            }

            val colorFilter =
                ColorFilter.tint(ColorProvider(if (isFavorite) Color.Red else Color.Gray))
            val provider =
                ImageProvider(resId = if (isFavorite) R.drawable.round_favorite_24 else R.drawable.round_favorite_border_24)
            Image(
                modifier = GlanceModifier.padding(8.dp)
                    .clickable(block = onFavorite),
                provider = provider,
                colorFilter = colorFilter,
                contentDescription = null
            )
        }
        Box(modifier = GlanceModifier.height(1.dp).fillMaxWidth().background(Color.LightGray)) {}
    }
}

@Composable
@GlanceComposable
private fun ChannelCardRtl(
    channel: RadioChannel,
    onClick: () -> Unit,
    onFavorite: () -> Unit
) = with(channel) {
    val context = LocalContext.current
    Column(modifier = GlanceModifier.clickable(block = onClick)) {
        Row(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(end = 16.dp, start = 8.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            val colorFilter =
                ColorFilter.tint(ColorProvider(if (isFavorite) Color.Red else Color.Gray))
            val provider =
                ImageProvider(resId = if (isFavorite) R.drawable.round_favorite_24 else R.drawable.round_favorite_border_24)
            Image(
                modifier = GlanceModifier.padding(8.dp)
                    .clickable(block = onFavorite),
                provider = provider,
                colorFilter = colorFilter,
                contentDescription = null
            )

            Column(modifier = GlanceModifier.defaultWeight()) {
                Text(
                    modifier = GlanceModifier.fillMaxWidth(),
                    text = name,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.run {
                        TextStyle(
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.Medium,
                            fontSize = fontSize
                        )
                    })

                Spacer(modifier = GlanceModifier.height(2.dp))
                Text(
                    modifier = GlanceModifier.fillMaxWidth(),
                    text = description.ifBlank { "بدون وصف" },
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.run {
                        TextStyle(
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.Normal,
                            color = GlanceTheme.colors.outline,
                            fontSize = fontSize
                        )
                    })
            }
        }
        Box(modifier = GlanceModifier.height(1.dp).fillMaxWidth().background(Color.LightGray)) {}
    }
}