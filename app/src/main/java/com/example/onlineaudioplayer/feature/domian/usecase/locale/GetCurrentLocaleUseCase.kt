package com.example.onlineaudioplayer.feature.domian.usecase.locale

import androidx.appcompat.app.AppCompatDelegate

class GetCurrentLocaleUseCase {
    operator fun invoke() = AppCompatDelegate.getApplicationLocales()[0]
}