package com.example.musicplayer.ui.theme


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.viewmodel.MusicPlayerViewModel
import FilesDisplay

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val musicPlayerViewModel: MusicPlayerViewModel = viewModel()

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            FilesDisplay(modifier = Modifier, navController, musicPlayerViewModel)
        }
        composable("CurrentSong/{songName}") { backStackEntry ->
            val songName = backStackEntry.arguments?.getString("songName") ?: "Unknown"
            CurrentSong(
                modifier = Modifier,
                songName = songName,
                artist = "artist",
                musicPlayerViewModel = musicPlayerViewModel
            )
        }
    }
}
