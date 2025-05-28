package com.example.musicplayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SongInfoResponse(
    @Json(name = "track")
    val track: Track?
)

@JsonClass(generateAdapter = true)
data class Track(
    @Json(name = "name")
    val name: String,
    @Json(name = "mbid")
    val mbid: String?,
    @Json(name = "url")
    val url: String,
    @Json(name = "duration")
    val duration: String,
    @Json(name = "streamable")
    val streamable: Streamable,
    @Json(name = "listeners")
    val listeners: String,
    @Json(name = "playcount")
    val playcount: String,
    @Json(name = "artist")
    val artist: Artist?,
    @Json(name = "album")
    val album: Album?,
    @Json(name = "toptags")
    val toptags: TopTags?,
    @Json(name = "wiki")
    val wiki: Wiki?
)

@JsonClass(generateAdapter = true)
data class Streamable(
    @Json(name = "#text")
    val text: String,
    @Json(name = "fulltrack")
    val fulltrack: String
)

@JsonClass(generateAdapter = true)
data class Artist(
    @Json(name = "name")
    val name: String,
    @Json(name = "mbid")
    val mbid: String?,
    @Json(name = "url")
    val url: String
)

@JsonClass(generateAdapter = true)
data class Album(
    @Json(name = "artist")
    val artist: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "mbid")
    val mbid: String?,
    @Json(name = "url")
    val url: String,
    @Json(name = "image")
    val image: List<Image>,
    @Json(name = "@attr")
    val attr: AlbumAttr?
)

@JsonClass(generateAdapter = true)
data class AlbumAttr(
    @Json(name = "position")
    val position: String
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "#text")
    val url: String,
    @Json(name = "size")
    val size: String
)

@JsonClass(generateAdapter = true)
data class TopTags(
    @Json(name = "tag")
    val tag: List<Tag>
)

@JsonClass(generateAdapter = true)
data class Tag(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@JsonClass(generateAdapter = true)
data class Wiki(
    @Json(name = "published")
    val published: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "content")
    val content: String
)
