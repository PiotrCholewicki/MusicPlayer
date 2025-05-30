package com.example.musicplayer.ui.theme

import FilesDisplay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.viewmodel.MusicPlayerViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
        .fillMaxSize(), // <- Zajmuje cały ekran
    navController: NavController,
    viewModel: MusicPlayerViewModel
) {
    Surface{
        Column(
            modifier = modifier.padding(8.dp) // główny kontener
        ) {
            // GÓRNY pasek - Raspberry (40.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("to_do: RASPBERRY")
            }

            // ŚRODKOWA lista utworów - reszta miejsca
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(stringResource(R.string.list_of_audio_tracks))

                FilesDisplay(
                    modifier = Modifier.weight(1f),
                    navController = navController,
                    musicPlayerViewModel = viewModel
                )
            }
        }
    }

}

@Composable
fun Footer(musicPlayerViewModel: MusicPlayerViewModel, navController: NavController) {

    val isPlaying by musicPlayerViewModel.isPlaying.collectAsState()
    val currentPosition by musicPlayerViewModel.currentPosition.collectAsState()
    val totalDuration by musicPlayerViewModel.totalDuration.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()

            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { musicPlayerViewModel.togglePlayPause() }) {
                Text(text = if (isPlaying) "Pause" else "Play")
            }
            Button(onClick = {
                musicPlayerViewModel.stopPlayback()
                navController.popBackStack()
            }) {
                Text("Stop")

            }
            Spacer(modifier = Modifier.width(8.dp))

        }

        Slider(
            value = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f,
            onValueChange = {
                val newPos = (it * totalDuration).toInt()
                musicPlayerViewModel.seekTo(newPos)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
