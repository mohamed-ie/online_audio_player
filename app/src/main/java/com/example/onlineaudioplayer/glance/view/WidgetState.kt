package com.example.onlineaudioplayer.glance.view

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

data class WidgetState(
    val playbackCardVisible: Boolean = false,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val channels: List<RadioChannel> = emptyList(),
    val name: String = "",
    val description: String = ""
)
