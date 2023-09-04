package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.delete_channel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme

@Composable
fun DeleteChannelDialog(
    state: DeleteChannelDialogState,
    onEvent: (ConfirmDialogEvent) -> Unit
) = with(state) {
    if (visible && channel != null)
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onEvent(ConfirmDialogEvent.Dismiss) },
            title = { Text(text = stringResource(id = R.string.delete_channel, channel.name)) },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.are_you_sure_you_want_to_delete_channel,
                        channel.name
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = { onEvent(ConfirmDialogEvent.Confirm) }) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { onEvent(ConfirmDialogEvent.Dismiss) }) {
                    Text(text = stringResource(id = R.string.dismiss))
                }
            }
        )
}

@Preview
@Composable
fun PreviewDeleteChannelDialog() {
    OnlineAudioPlayerTheme {
        DeleteChannelDialog(
            state = DeleteChannelDialogState(
                visible = true,
                channel = RadioChannel(name = "Quran", description = "Egyptian Quran", url = "")
            )
        ) {}
    }
}