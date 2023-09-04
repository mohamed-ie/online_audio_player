package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.delete_channel

sealed interface ConfirmDialogEvent{
    object Confirm:ConfirmDialogEvent
    object Dismiss:ConfirmDialogEvent
}