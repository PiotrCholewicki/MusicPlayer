package com.example.musicplayer.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.viewmodel.MusicPlayerViewModel
import com.example.musicplayer.viewmodel.TrackViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
        .fillMaxSize(), // <- Zajmuje cały ekran
    navController: NavController,
    viewModel: MusicPlayerViewModel,
    trackViewModel: TrackViewModel

) {

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.padding(8.dp).fillMaxSize() // główny kontener
        ) {

            // MIDDLE BAR
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),

                ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.list_of_audio_tracks),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    FilesDisplay(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        musicPlayerViewModel = viewModel,
                        trackViewModel = trackViewModel
                    )
                }

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
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { musicPlayerViewModel.togglePlayPause() }) {
                if (isPlaying) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.pause_button),
                        contentDescription = "Pause"
                    )

                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.play_button),
                        contentDescription = "Play"
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                musicPlayerViewModel.stopPlayback()
                navController.navigate("MainScreen")
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.stop_button),
                    contentDescription = "Stop"
                )
            }
        }
        Row(){
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
}

@Composable
fun FilesDisplay(
    modifier: Modifier = Modifier,
    navController: NavController,
    musicPlayerViewModel: MusicPlayerViewModel,
    trackViewModel: TrackViewModel
) {

    val currentFile by musicPlayerViewModel.currentFile.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val tracks by trackViewModel.tracks.observeAsState(emptyList())
        tracks.forEach { track ->
            val trackTitle = track.name
            val trackArtist = track.artist
            val currentTrack = track.path
            TextButton(
                onClick = {
                    if(currentFile != currentTrack){ //dont restart the player when clicked the same song
                        musicPlayerViewModel.playFile(track.path)
                        Log.e("MainScreen", "$currentFile, $currentTrack")
                    }
                    navController.navigate("CurrentSong/${track.id}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$trackArtist - $trackTitle")
                }
            }
        }


        if (currentFile != null) {
            Spacer(modifier = Modifier.weight(1f)) // wypycha slider na dół
            Footer(musicPlayerViewModel, navController)
        }
    }
}
