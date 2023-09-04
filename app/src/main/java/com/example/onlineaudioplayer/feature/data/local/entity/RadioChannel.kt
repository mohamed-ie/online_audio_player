package com.example.onlineaudioplayer.feature.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("channel")
data class RadioChannel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val url: String,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean=false
)
