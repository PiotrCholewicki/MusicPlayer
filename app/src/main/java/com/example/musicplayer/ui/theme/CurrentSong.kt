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

import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp


import androidx.navigation.NavController
import com.example.musicplayer.viewmodel.MusicPlayerViewModel

@Composable
fun CurrentSong(
    modifier: Modifier = Modifier,
    songName: String,
    artist: String,
    musicPlayerViewModel: MusicPlayerViewModel,
    navController: NavController
) {


    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())) {
            Row {
                Text("Cover Art")
            }
            Row {
                Text("Now playing: $songName")
            }
            Row {
                Text("Artist: $artist")
            }
            Spacer(modifier = Modifier.weight(1f)) // wypycha slider na dół
            Row {
                Footer(musicPlayerViewModel, navController)

            }
        }
    }
}

