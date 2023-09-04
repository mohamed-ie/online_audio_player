package com.example.onlineaudioplayer.core.base

import androidx.lifecycle.ViewModel
import com.example.onlineaudioplayer.core.utils.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(ScreenState.LOADING)
    val screenState = _screenState.asStateFlow()

    protected fun toStableScreenState() = _screenState.update { ScreenState.STABLE }
    protected fun toLoadingScreenState() = _screenState.update { ScreenState.LOADING }

}