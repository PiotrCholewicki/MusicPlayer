package com.example.musicplayer

import android.app.Application
import com.example.musicplayer.data.database.TrackDatabase
import com.example.musicplayer.data.repository.TrackRepository

class MusicPlayerApplication: Application() {
    private val database by lazy { TrackDatabase.getDatabase(this) }
    val repository by lazy { TrackRepository(database.trackDao()) }
}

