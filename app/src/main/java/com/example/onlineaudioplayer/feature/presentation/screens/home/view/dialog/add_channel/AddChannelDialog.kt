package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.add_channel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.core.utils.UIText
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme

@Composable
fun AddChannelDialog(
    state: AddChannelDialogState,
    onEvent: (AddChannelEvent) -> Unit
) = with(state) {
    if (visible)
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onEvent(AddChannelEvent.Dismiss) },
            title = { Text(text = stringResource(id = R.string.new_channel)) },
            text = {
                val focusManager = LocalFocusManager.current
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        shape = MaterialTheme.shapes.medium,
                        isError = nameError,
                        supportingText = errorText(nameErrorMessage),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        }),
                        label = { Text(text = stringResource(id = R.string.name)) },
                        onValueChange = { onEvent(AddChannelEvent.NameChanged(it)) })

                    OutlinedTextField(
                        value = description,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        shape = MaterialTheme.shapes.medium,
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        label = { Text(text = stringResource(id = R.string.description)) },
                        onValueChange = { onEvent(AddChannelEvent.DescriptionChanged(it)) })


                    OutlinedTextField(
                        value = url,
                        isError = urlError,
                        shape = MaterialTheme.shapes.medium,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            onEvent(AddChannelEvent.Save)
                        }),
                        label = { Text(text = stringResource(id = R.string.url)) },
                        supportingText = errorText(urlErrorMessage),
                        onValueChange = { onEvent(AddChannelEvent.UrlChanged(it)) })
                }
            },
            confirmButton = {
                TextButton(onClick = { onEvent(AddChannelEvent.Save) }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = { onEvent(AddChannelEvent.Dismiss) }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
}


private fun errorText(error: UIText?): (@Composable () -> Unit)? = error?.let {
    {
        Text(text = error.asString(), color = MaterialTheme.colorScheme.error)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAddChannelDialog() {
    OnlineAudioPlayerTheme {
        AddChannelDialog(AddChannelDialogState(visible = true), onEvent = {})
    }
}