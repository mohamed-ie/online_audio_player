package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.languages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme

private val languages = mapOf(
    R.string.arabic to "ar",
    R.string.english to "en"
)

@Composable
fun LanguagesDialog(
    state: LanguageDialogState,
    onEvent: (LanguageDialogEvent) -> Unit
) = with(state) {
    if (visible)
        Dialog(onDismissRequest = { onEvent(LanguageDialogEvent.Dismiss) }) {
            Card(
                shape = AlertDialogDefaults.shape,
                colors = CardDefaults.cardColors(containerColor = AlertDialogDefaults.containerColor),
                elevation = CardDefaults.cardElevation(defaultElevation = AlertDialogDefaults.TonalElevation)
            ) {
                Column(
                    Modifier
                        .padding(vertical = 24.dp)
                        .selectableGroup()
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(id = R.string.language),
                        style = MaterialTheme.typography.headlineSmall,
                        color = AlertDialogDefaults.titleContentColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    languages.forEach { (key, value) ->
                        DialogLanguageCard(
                            text = stringResource(id = key),
                            isSelected = value == selected,
                            onSelect = { onEvent(LanguageDialogEvent.LanguageChanged(value)) })
                    }

                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { onEvent(LanguageDialogEvent.Dismiss) }) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            enabled = isSaveEnabled,
                            onClick = { onEvent(LanguageDialogEvent.Save) }) {
                            Text(text = stringResource(id = R.string.save))
                        }
                    }
                }
            }
        }
}


@Preview
@Composable
fun PreviewSettingsDialog() {
    OnlineAudioPlayerTheme {
        LanguagesDialog(LanguageDialogState(visible = true)) {}
    }
}