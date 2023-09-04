package com.example.onlineaudioplayer.glance.view.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceComposable
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.onlineaudioplayer.R

@GlanceComposable
@Composable
fun PlaybackCard(
    name: String,
    description: String,
    isPlaying: Boolean,
    isLoading: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    isRtl: Boolean = false
) = if (isRtl) PlaybackCardRtl(
    name = name,
    description = description,
    isPlaying = isPlaying,
    isLoading = isLoading,
    onPlay = onPlay,
    onPause = onPause,
    onStop = onStop
)
else PlaybackCardLtr(
    name = name,
    description = description,
    isPlaying = isPlaying,
    isLoading = isLoading,
    onPlay = onPlay,
    onPause = onPause,
    onStop = onStop
)

@GlanceComposable
@Composable
private fun PlaybackCardLtr(
    name: String,
    description: String,
    isPlaying: Boolean,
    isLoading: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
) {
    val context = LocalContext.current
    val height = MaterialTheme.typography.titleLarge.lineHeight.value +
            MaterialTheme.typography.labelMedium.lineHeight.value +
            16.dp.value

    Box {
        Image(
            modifier = GlanceModifier.fillMaxWidth().height(height.dp),
            provider = ImageProvider(R.drawable.background_widget_playback),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.primary)
        )
        Row(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Column(modifier = GlanceModifier.fillMaxWidth().defaultWeight()) {
                Text(
                    text = name, style = MaterialTheme.typography.titleLarge.run {
                        TextStyle(
                            color = GlanceTheme.colors.onPrimary,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Medium
                        )
                    }, maxLines = 1
                )

                Text(
                    text = description.ifBlank { context.getString(R.string.no_description) },
                    style = MaterialTheme.typography.labelMedium.run {
                        TextStyle(
                            color = ColorProvider(MaterialTheme.colorScheme.outlineVariant),
                            fontSize = fontSize,
                        )
                    },
                    maxLines = 1
                )
            }

            if (isLoading) {
                Spacer(modifier = GlanceModifier.width(8.dp))

                CircularProgressIndicator(
                    modifier = GlanceModifier.size(24.dp), color = GlanceTheme.colors.onPrimary
                )
            }

            Spacer(modifier = GlanceModifier.width(8.dp))

            val colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary)
            val provider =
                ImageProvider(if (isPlaying) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24)
            Image(
                modifier = GlanceModifier.padding(8.dp)
                    .clickable(block = if (isPlaying) onPause else onPlay),
                provider = provider,
                colorFilter = colorFilter,
                contentDescription = context.getString(if (isPlaying) R.string.pause else R.string.play)
            )

            Image(
                modifier = GlanceModifier.padding(8.dp).clickable(block = onStop),
                provider = ImageProvider(resId = R.drawable.round_stop_24),
                colorFilter = colorFilter,
                contentDescription = context.getString(R.string.stop)
            )

        }
    }
}

@GlanceComposable
@Composable
private fun PlaybackCardRtl(
    name: String,
    description: String,
    isPlaying: Boolean,
    isLoading: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
) {
    val context = LocalContext.current
    val height = MaterialTheme.typography.titleLarge.lineHeight.value +
            MaterialTheme.typography.labelMedium.lineHeight.value +
            16.dp.value

    Box {
        Image(
            modifier = GlanceModifier.fillMaxWidth().height(height.dp),
            provider = ImageProvider(R.drawable.background_widget_playback),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.primary)
        )

        Row(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {

            val colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary)
            Image(
                modifier = GlanceModifier.padding(8.dp).clickable(block = onStop),
                provider = ImageProvider(resId = R.drawable.round_stop_24),
                colorFilter = colorFilter,
                contentDescription = context.getString(R.string.stop)
            )

            val provider =
                ImageProvider(if (isPlaying) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24)
            Image(
                modifier = GlanceModifier.padding(8.dp)
                    .clickable(block = if (isPlaying) onPause else onPlay),
                provider = provider,
                colorFilter = colorFilter,
                contentDescription = context.getString(if (isPlaying) R.string.pause else R.string.play)
            )

            if (isLoading) {
                Spacer(modifier = GlanceModifier.width(8.dp))

                CircularProgressIndicator(
                    modifier = GlanceModifier.size(24.dp), color = GlanceTheme.colors.onPrimary
                )
            }
            Spacer(modifier = GlanceModifier.width(8.dp))

            Column(modifier = GlanceModifier.fillMaxWidth().defaultWeight()) {
                Text(
                    modifier = GlanceModifier.fillMaxWidth(),
                    text = name,
                    style = MaterialTheme.typography.titleLarge.run {
                        TextStyle(
                            textAlign = TextAlign.End,
                            color = GlanceTheme.colors.onPrimary,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    maxLines = 1
                )

                Text(
                    modifier = GlanceModifier.fillMaxWidth(),
                    text = description.ifBlank { "بدون وصف" },
                    style = MaterialTheme.typography.labelMedium.run {
                        TextStyle(
                            textAlign = TextAlign.End,
                            color = ColorProvider(MaterialTheme.colorScheme.outlineVariant),
                            fontSize = fontSize,
                        )
                    },
                    maxLines = 1
                )
            }
        }
    }
}