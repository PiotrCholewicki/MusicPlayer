package com.example.musicplayer.data.repository

import androidx.annotation.WorkerThread
import com.example.musicplayer.data.database.Track
import com.example.musicplayer.data.dao.TrackDao
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