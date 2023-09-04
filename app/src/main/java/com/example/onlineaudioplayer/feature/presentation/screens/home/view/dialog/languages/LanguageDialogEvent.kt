package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.languages

sealed interface LanguageDialogEvent {
    object Dismiss : LanguageDialogEvent
    object Save : LanguageDialogEvent
    class LanguageChanged(val language: String) : LanguageDialogEvent
}