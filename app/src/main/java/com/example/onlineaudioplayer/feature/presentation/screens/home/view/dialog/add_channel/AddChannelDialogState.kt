package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.add_channel

import com.example.onlineaudioplayer.core.utils.UIText

data class AddChannelDialogState(
    val visible: Boolean = false,
    val editMode: Boolean = false,
    val name: String = "",
    val nameErrorMessage: UIText? = null,
    val nameError: Boolean = false,
    val url: String = "",
    val urlErrorMessage: UIText? = null,
    val urlError: Boolean = false,
    val description: String = "",
)