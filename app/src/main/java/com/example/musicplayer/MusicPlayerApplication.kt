package com.example.musicplayer

import android.app.Application
import com.example.musicplayer.data.database.TrackDatabase
import com.example.musicplayer.data.repository.TrackRepository
import com.example.musicplayer.data.database.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MusicPlayerApplication : Application() {
    private val database by lazy { TrackDatabase.getDatabase(this) }
    val repository by lazy { TrackRepository(database.trackDao()) }

    override fun onCreate() {
        super.onCreate()
        initializeDatabaseIfEmpty()
    }

    private fun initializeDatabaseIfEmpty() {
        CoroutineScope(Dispatchers.IO).launch {
            val currentTracks = repository.allTracks.first()
            if (currentTracks.isEmpty()) {
                val sampleTracks = listOf(
                    Track(0, "Nirvana", "Lithium", "audio_files/lithium.mp3"),
                    Track(0, "The Beatles", "Hey Jude", "audio_files/hey_jude.mp3")
                )
                sampleTracks.forEach {
                    repository.insertTrack(it)
                }
            }
        }
    }
}
