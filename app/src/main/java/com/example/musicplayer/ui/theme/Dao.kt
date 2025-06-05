package com.example.musicplayer.ui.theme


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track)

    @Update suspend fun updateTrack(track: Track)

    @Query("SELECT * FROM tracks WHERE id = :trackId")
    suspend fun getTrackById(trackId: Int): Track?

    @Query("SELECT * FROM tracks")
    fun allTracks(): Flow<List<Track>>

    @Delete
    suspend fun deleteTrack(track: Track)
}