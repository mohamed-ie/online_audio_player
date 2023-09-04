package com.example.onlineaudioplayer.feature.presentation.screens.home.view

sealed interface HomeEvent{
    object ShowAddChannelDialog : HomeEvent
    object Play : HomeEvent
    object Pause : HomeEvent
    object Stop : HomeEvent
    object ShowLanguageDialog : HomeEvent
}