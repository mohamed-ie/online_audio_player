package com.example.onlineaudioplayer.feature.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import com.example.onlineaudioplayer.feature.presentation.screens.home.HomeScreen
import com.example.onlineaudioplayer.feature.presentation.ui.theme.OnlineAudioPlayerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AppCompatDelegate.getApplicationLocales().isEmpty)
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))

        setContent {
            OnlineAudioPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel = koinViewModel())
                }
            }
        }
    }
}

