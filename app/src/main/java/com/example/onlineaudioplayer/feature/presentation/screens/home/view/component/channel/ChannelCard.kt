package com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.channel

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ChannelCard(
    channel: RadioChannel,
    onEvent: (ChannelEvent) -> Unit
) = with(channel) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    var maxOffsetX by remember { mutableIntStateOf(0) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    Column(modifier = Modifier.animateItemPlacement()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .onSizeChanged { maxOffsetX = it.width }
                .align(Alignment.CenterEnd)) {
                FilledTonalIconButton(onClick = { onEvent(ChannelEvent.ShowEditDialog(channel)) }) {
                    Icon(
                        imageVector = Icons.TwoTone.Edit,
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = null
                    )
                }

                FilledTonalIconButton(
                    onClick = { onEvent(ChannelEvent.ShowDeleteDialog(channel)) },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Row(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), 0) }
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .clickable(onClick = {
                        if (offsetX != 0f)
                            offsetX = 0f
                        else
                            onEvent(ChannelEvent.Play(channel))
                    })
                    .draggable(
                        state = rememberDraggableState(onDelta = {
                            if (isRtl) {
                                if (offsetX - it <= 0)
                                    offsetX -= it
                                return@rememberDraggableState
                            }
                            if (offsetX + it <= 0)
                                offsetX += it
                        }),
                        orientation = Orientation.Horizontal,
                        onDragStopped = {
                            offsetX =
                                if (isRtl && offsetX > maxOffsetX) maxOffsetX.toFloat() else if (offsetX < -maxOffsetX) -maxOffsetX.toFloat() else 0f
                        }
                    )
                    .padding(vertical = 12.dp)
                    .padding(start = 16.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = description.ifBlank { stringResource(id = R.string.no_description) } ,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                IconButton(onClick = { onEvent(ChannelEvent.FavoriteToggle(channel)) }) {
                    Crossfade(targetState = channel.isFavorite, label = "favorite_button") {
                        if (it)
                            Icon(
                                imageVector = Icons.Rounded.Favorite,
                                tint = Color.Red,
                                contentDescription = stringResource(id = R.string.favorite)
                            )
                        else
                            Icon(
                                imageVector = Icons.Rounded.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.favorite)
                            )
                    }
                }
            }
        }
        Divider()
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewChannelCard() {
    var isFavorite by remember {
        mutableStateOf(false)
    }
    OnlineAudioPlayerTheme {
        LazyColumn {
            item {
                ChannelCard(
                    RadioChannel(
                        name = "Quran",
                        description = "Holy Quran Radio from Cairo",
                        url = "",
                        isFavorite = isFavorite
                    )) {
                    if (it is ChannelEvent.FavoriteToggle)
                        isFavorite = !isFavorite
                }
            }
        }
    }
}
