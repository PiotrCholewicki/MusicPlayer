package com.example.musicplayer.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.viewmodel.MusicPlayerViewModel
import androidx.compose.runtime.getValue
import com.example.musicplayer.ui.screen.CurrentSong
import com.example.musicplayer.ui.screen.MainScreen
import com.example.musicplayer.viewmodel.TrackViewModel

@Composable
fun AppNavigation(trackViewModel: TrackViewModel) {
    val navController = rememberNavController()
    val musicPlayerViewModel: MusicPlayerViewModel = viewModel()

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(modifier = Modifier, navController, musicPlayerViewModel, trackViewModel)
        }
        composable("CurrentSong/{trackId}") { backStackEntry ->
            val trackId = backStackEntry.arguments?.getString("trackId") ?: ""

            val tracks by trackViewModel.tracks.observeAsState(emptyList())
            val track = tracks.find { it.id.toString() == trackId }
            CurrentSong(
                modifier = Modifier,
                songName = track?.name ?: "Wrong song name",
                artist = track?.artist ?: "Wrong artist",
                musicPlayerViewModel = musicPlayerViewModel,
                navController = navController
            )
        }
    }


}
