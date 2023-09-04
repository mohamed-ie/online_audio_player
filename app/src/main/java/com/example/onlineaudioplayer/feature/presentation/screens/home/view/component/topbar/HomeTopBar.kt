package com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme

@Composable
fun HomeTopBar(onLanguageClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.welcome),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.headlineLarge
        )

        FilledTonalIconButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = onLanguageClick
        ) {
            Icon(
                imageVector = Icons.Rounded.Language,
                contentDescription = stringResource(id = R.string.language)
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeTopBar() {
    OnlineAudioPlayerTheme {
        HomeTopBar{}
    }
}