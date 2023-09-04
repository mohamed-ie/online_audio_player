package com.example.onlineaudioplayer.feature.domian.model

import com.example.onlineaudioplayer.core.utils.UIText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UIText? = null
)
