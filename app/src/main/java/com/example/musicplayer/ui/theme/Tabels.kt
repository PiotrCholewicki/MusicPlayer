package com.example.musicplayer.ui.theme

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.stream.IntStream.IntMapMultiConsumer

@Entity(tableName="artists")
data class  ArtistEntity(
    @PrimaryKey val mbid: String,
    val name: String,
    val url: String?
)

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val mbid: String,
    val artistName: String,
    val title: String,
    val url: String?,
    val imageUrlSmall: String?,
    val imageUrlMedium: String?,
    val imageUrlLarge: String?
)

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val duration: Int,
    val streamable: Boolean?,
    val listeners: Int?,
    val playcoun: Int?,
    @Embedded(prefix = "artist_") val artist: ArtisrRef,
    @Embedded(prefix = "album_") val album: AlbumRef,
    val wikiPublished: String?,
    val wikiSummary: String?,
    val wikiContent: String?
)

data class ArtisrRef(
    val mbid: String,
    val name: String,
    val url: String?
)

data class AlbumRef(
    val mbid: String,
    val artistName: String,
    val title: String,
    val url: String?,
    val imageUrlSmall: String?,
    val imageUrlMedium: String?,
    val imageUrlLarge: String?
)