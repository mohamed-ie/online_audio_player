package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.languages

data class LanguageDialogState(
    val selected: String = "en",
    val isSaveEnabled: Boolean = false,
    val visible: Boolean = false
)