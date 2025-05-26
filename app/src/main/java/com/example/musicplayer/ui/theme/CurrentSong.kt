package com.example.musicplayer.ui.theme

import android.media.MediaPlayer
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.getValue
import com.example.musicplayer.viewmodel.MusicPlayerViewModel

@Composable
fun CurrentSong(
    modifier: Modifier = Modifier,
    songName: String,
    artist: String,
    musicPlayerViewModel: MusicPlayerViewModel
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
                Footer(musicPlayerViewModel)

            }
        }
    }
}

