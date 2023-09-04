package com.example.onlineaudioplayer.feature.presentation.screens.home.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.bottombar.HomeBottomBar
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.channel.ChannelCard
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.channel.ChannelEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.topbar.HomeTopBar
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme

@Composable
fun HomeScreenContent(
    snackbarHostState: SnackbarHostState,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onChannelEvent: (ChannelEvent) -> Unit,
) = with(state) {
    Scaffold(bottomBar = {
        HomeBottomBar(visible = bottomBarVisible,
            title = playbackTitle,
            description = playbackDescription,
            isPlaying = isPlaying,
            isLoading = isLoading,
            onPlay = { onEvent(HomeEvent.Play) },
            onPause = { onEvent(HomeEvent.Pause) },
            onStop = { onEvent(HomeEvent.Stop) })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(HomeEvent.ShowAddChannelDialog) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.new_channel)
                )
            }
        },
        topBar = { HomeTopBar(onLanguageClick = { onEvent(HomeEvent.ShowLanguageDialog) }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (channels.isEmpty()) item {
                Text(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.no_channels),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            else items(items = channels, key = RadioChannel::id) { channel ->
                ChannelCard(
                    channel = channel, onEvent = onChannelEvent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenContent() {
    OnlineAudioPlayerTheme {
        HomeScreenContent(snackbarHostState = SnackbarHostState(), state = HomeState(
            channels = listOf(
                RadioChannel(
                    name = "Quran",
                    description = "Quran Radio from Cairo",
                    url = "http://n02.radiojar.com/8s5u5tpdtwzuv?rj-ttl=5&rj-tok=AAABid_AR9MA2rJbJR6Glr-zpQ"
                ), RadioChannel(
                    id = 1,
                    name = "Quran1",
                    description = "Quran Radio from Cairo",
                    url = "http://n02.radiojar.com/8s5u5tpdtwzuv?rj-ttl=5&rj-tok=AAABid_AR9MA2rJbJR6Glr-zpQ"
                )
            )
        ), onEvent = {}, {})
    }
}