package com.example.onlineaudioplayer.feature.presentation.screens.home

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.onlineaudioplayer.core.utils.ScreenState
import com.example.onlineaudioplayer.core.utils.UIText
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.HomeScreenContent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.HomeUIEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.add_channel.AddChannelDialog
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.delete_channel.DeleteChannelDialog
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.languages.LanguagesDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        viewModel.uiEvent.onEach { uiEvent ->
            when (uiEvent) {
                is HomeUIEvent.ShowError ->
                    showSnackbar(snackbarHostState, context, uiEvent.errorMessage)
            }
        }.launchIn(this)
    })

    AnimatedContent(targetState = viewModel.screenState.collectAsState().value, label = "") {
        when (it) {
            ScreenState.LOADING -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            ScreenState.STABLE -> HomeScreenContent(
                snackbarHostState = snackbarHostState,
                state = viewModel.state.collectAsState().value,
                onEvent = viewModel::onEvent,
                onChannelEvent = viewModel::onChannelEvent
            )
        }
    }


    AddChannelDialog(
        state = viewModel.addChannelDialogState.collectAsState().value,
        onEvent = viewModel::onAddChannelDialogEvent
    )

    DeleteChannelDialog(
        state = viewModel.deleteDialogState.collectAsState().value,
        onEvent = viewModel::onDeleteDialogEvent
    )

    LanguagesDialog(
        state = viewModel.languageDialogState.collectAsState().value,
        onEvent = viewModel::onLanguageDialogEvent
    )

}

private suspend fun showSnackbar(
    snackbarHostState: SnackbarHostState,
    context: Context,
    errorMessage: UIText
) {
    val message = when (errorMessage) {
        is UIText.DynamicString -> errorMessage.value
        is UIText.StringResource -> context.getString(errorMessage.resId)
    }

    snackbarHostState.showSnackbar(
        message = message,
        withDismissAction = true,
        duration = SnackbarDuration.Long
    )
}