package com.example.onlineaudioplayer.glance.view

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

sealed interface WidgetEvent {
    object Stop : WidgetEvent
    object Pause : WidgetEvent

    class FavoriteChanged(val channel: RadioChannel) : WidgetEvent
    class Play(val channel: RadioChannel?=null) : WidgetEvent
}