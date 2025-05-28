package com.example.musicplayer.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize


import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


import androidx.compose.ui.Modifier

import androidx.compose.runtime.getValue

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


import androidx.navigation.NavController

import com.example.musicplayer.viewmodel.MusicPlayerViewModel


@Composable
fun CurrentSong(
    modifier: Modifier = Modifier,
    songName: String,
    artist: String,
    musicPlayerViewModel: MusicPlayerViewModel,
    navController: NavController,
    viewModel: SongInfoViewModel = viewModel()
) {

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row { Text("Cover Art") }
            Row { Text("Now playing: $songName") }
            Row { Text("Artist: $artist") }

            Row {
                //api display
                val viewModel: SongInfoViewModel = viewModel()

                LaunchedEffect(songName, artist) {
                    viewModel.fetchSongInfo(
                        apiKey = "2a1e7bb8dbb77494cb8334cd8607ab0a",
                        songName = "lithium",
                        artist = "nirvana"
                    )

                }
                val songInfo by viewModel.songInfo
                songInfo?.let { info ->
                    Row { Text("Tytuł utworu z API: ${info.name}") }

                    Spacer(modifier.padding(4.dp))

                    val minutes = info.duration.toInt()/1000/60
                    val seconds = info.duration.toInt()/1000 - minutes * 60
                    Row {Text("Długość: ${minutes}:${seconds}")}
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row { Footer(musicPlayerViewModel, navController) }
        }


    }

}




