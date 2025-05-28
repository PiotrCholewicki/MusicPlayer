package com.example.musicplayer.ui.theme

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TrackEntity::class, ArtistEntity::class, AlbumEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun trackDao(): TrackDao
    abstract fun artistDao(): ArtistDao
    abstract fun albumDao(): AlbumDao
}