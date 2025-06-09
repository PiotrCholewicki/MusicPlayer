package com.example.musicplayer.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController,
    viewModel: MusicPlayerViewModel,
    trackViewModel: TrackViewModel
) {

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = modifier.padding(16.dp).fillMaxSize(), // Increased overall padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.list_of_audio_tracks),
                    fontSize = 24.sp, // Larger font for title
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary // Theme primary color for title
                )
            }

            // MIDDLE BAR (LazyColumn for Files Display)
            FilesDisplay(
                modifier = Modifier
                    .weight(1f) // Takes remaining space
                    .fillMaxWidth(),
                navController = navController,
                musicPlayerViewModel = viewModel,
                trackViewModel = trackViewModel
            )

            // Footer (Music Player Controls)
            // The footer will be placed here automatically after FilesDisplay due to weight
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
            .padding(top = 16.dp, bottom = 8.dp), // Added top padding to separate from list
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f,
            onValueChange = {
                val newPos = (it * totalDuration).toInt()
                musicPlayerViewModel.seekTo(newPos)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            colors = androidx.compose.material3.SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer between slider and buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // Center buttons
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { musicPlayerViewModel.togglePlayPause() },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) ImageVector.vectorResource(R.drawable.pause_button) else ImageVector.vectorResource(R.drawable.play_button),
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.onPrimary // Icon color
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    musicPlayerViewModel.stopPlayback()
                    // You might want to navigate only if the current screen is not MainScreen to avoid re-composition issues
                    if (navController.currentBackStackEntry?.destination?.route != "MainScreen") {
                        navController.navigate("MainScreen") {
                            // Pop up to MainScreen to clear the back stack if needed
                            popUpTo("MainScreen") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.stop_button),
                    contentDescription = "Stop",
                    tint = MaterialTheme.colorScheme.onPrimary // Icon color
                )
            }
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
    val tracks by trackViewModel.tracks.observeAsState(emptyList())

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                                .height(700.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between track items
        ) {
            items(tracks) { track ->
                val trackTitle = track.name
                val trackArtist = track.artist
                val currentTrackPath = track.path

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (currentFile != currentTrackPath) { // don't restart the player when clicked the same song
                                musicPlayerViewModel.playFile(track.path)
                                Log.e("MainScreen", "$currentFile, $currentTrackPath")
                            }
                            navController.navigate("CurrentSong/${track.id}")
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Subtle shadow
                    colors = CardDefaults.cardColors(
                        containerColor = if (currentFile == currentTrackPath) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant // Highlight current playing track
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Padding inside the card
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = trackTitle,
                                style = MaterialTheme.typography.titleMedium, // Material 3 typography
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = trackArtist,
                                style = MaterialTheme.typography.bodySmall, // Material 3 typography
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }

        // The footer is now handled outside of FilesDisplay, directly in MainScreen's Column
        // This ensures it stays at the bottom and isn't part of the scrollable list.
        if (currentFile != null) {
            Footer(musicPlayerViewModel, navController)
        }
    }
}