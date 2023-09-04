package com.example.onlineaudioplayer.glance

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import org.koin.java.KoinJavaComponent

class AppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget by KoinJavaComponent.inject(AppWidget::class.java)
}