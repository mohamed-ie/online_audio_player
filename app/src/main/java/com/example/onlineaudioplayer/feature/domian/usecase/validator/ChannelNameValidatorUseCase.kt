package com.example.onlineaudioplayer.feature.domian.usecase.validator

import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.model.ValidationResult
import com.example.onlineaudioplayer.core.utils.UIText

class ChannelNameValidatorUseCase {

    operator fun invoke(
        name: String,
        channels: Collection<RadioChannel>
    ): ValidationResult {
        if (name.isBlank())
            return ValidationResult(
                successful = false,
                errorMessage = UIText.StringResource(R.string.required)
            )

        if (channels.any { it.name == name })
            return ValidationResult(
                successful = false,
                errorMessage = UIText.StringResource(R.string.name_already_exist)
            )

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}