package com.example.musicplayer.ui.theme

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TrackRepository(private val trackDao: TrackDao){
    val allTracks: Flow<List<Track>> = trackDao.allTracks()

    @WorkerThread
    suspend fun insertTrack(track: Track){
        trackDao.insertTrack(track)
    }

    @WorkerThread
    suspend fun updateTrack(track: Track){
        trackDao.updateTrack(track)
    }

    @WorkerThread
    suspend fun getTrackById(trackId: Int){
        trackDao.getTrackById(trackId)
    }
    @WorkerThread
    suspend fun deleteTrack(track: Track){
        trackDao.deleteTrack(track)
    }


}