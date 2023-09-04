package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.delete_channel

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

data class DeleteChannelDialogState(
    val visible: Boolean = false,
    val channel: RadioChannel? = null
)
