package com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.languages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme

@Composable
fun DialogLanguageCard(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = isSelected,
                onClick = onSelect,
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = AlertDialogDefaults.textContentColor,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewLanguageItemCard() {
    OnlineAudioPlayerTheme {
        DialogLanguageCard(stringResource(id = R.string.arabic),true) {}
    }
}