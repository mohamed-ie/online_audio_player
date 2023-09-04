package com.example.onlineaudioplayer.feature.domian.usecase.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class ChangeLocaleUseCase {
    operator fun invoke(language: String) =
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
}