package com.example.onlineaudioplayer.feature.presentation.screens.home.view

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

data class HomeState(
    val playbackTitle:String="",
    val playbackDescription:String="",
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val bottomBarVisible: Boolean = false,
    val channels: List<RadioChannel> = emptyList(),
)
