package com.example.musicplayer.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tracks")
data class Track(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val artist: String,
    val name: String,
    val path: String
)