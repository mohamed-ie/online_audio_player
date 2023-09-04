package com.example.onlineaudioplayer.feature.domian.usecase.validator

import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.model.ValidationResult
import com.example.onlineaudioplayer.core.utils.UIText

class ChannelUrlValidatorUseCase {
    private val regex by lazy { "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)".toRegex() }

    operator fun invoke(
        name: String,
        channels: Collection<RadioChannel>
    ): ValidationResult {
        if (name.isBlank())
            return ValidationResult(
                successful = false,
                errorMessage = UIText.StringResource(R.string.required)
            )
        if (!regex.matches(name))
            return ValidationResult(
                successful = false,
                errorMessage = UIText.StringResource(R.string.not_valid_url)
            )
        channels.find { it.url == name }?.let {
            val error = UIText.StringResource(R.string.channel_have_same_url, it.name)
            return ValidationResult(successful = false, errorMessage = error)
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}