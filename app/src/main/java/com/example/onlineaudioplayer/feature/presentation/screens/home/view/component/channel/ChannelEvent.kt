package com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.channel

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

sealed interface ChannelEvent {
    class Play(val channel: RadioChannel) : ChannelEvent
    class ShowEditDialog(val channel: RadioChannel) : ChannelEvent
    class ShowDeleteDialog(val channel: RadioChannel) : ChannelEvent
    class FavoriteToggle(val channel: RadioChannel) : ChannelEvent
}
