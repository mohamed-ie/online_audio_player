package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.add_channel

sealed interface AddChannelEvent {
    class NameChanged(val value: String) : AddChannelEvent
    class DescriptionChanged(val value: String) : AddChannelEvent
    class UrlChanged(val value: String) : AddChannelEvent

    object Dismiss : AddChannelEvent
    object Save : AddChannelEvent

}
