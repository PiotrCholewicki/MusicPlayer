package com.example.musicplayer.ui.theme

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TrackDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Update
    suspend fun updateTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE id = :trackId")
    suspend fun getTrackById(trackId: Int): TrackEntity?

    @Query("SELECT * FROM tracks")
    suspend fun getAllTracks(): List<TrackEntity>

    @Query("DELETE FROM tracks WHERE id = :trackId")
    suspend fun deleteTrackById(trackId: Int)

    @Query("SELECT * FROM tracks WHERE artist_mbid = :artistMbid")
    suspend fun getTrackByArtist(artistMbid: String): List<TrackEntity>
}

@Dao
interface ArtistDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: ArtistEntity)

    @Update
    suspend fun updateArtist(artist: ArtistEntity)

    @Query("SELECT * FROM artists WHERE mbid = :artistMbid")
    suspend fun getAtristById(artistMbid: String): ArtistEntity?

    @Query("SELECT * FROM artists")
    suspend fun selectAllArtists(): List<ArtistEntity>
}

@Dao
interface AlbumDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity)

    @Update
    suspend fun updateAlbum(album: AlbumEntity)

    @Query("SELECT * FROM albums WHERE mbid = :albumMbid")
    suspend fun selectAlbumById(albumMbid: String): AlbumEntity?

    @Query("SELECT * FROM albums")
    suspend fun selectAllAlbums(): List<AlbumEntity>
}