package com.example.onlineaudioplayer.feature.presentation.screens.home.view

import com.example.onlineaudioplayer.core.utils.UIText

sealed interface HomeUIEvent{
    class ShowError(val errorMessage:UIText): HomeUIEvent
}