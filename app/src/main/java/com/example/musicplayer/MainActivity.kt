package com.example.musicplayer

import FilesDisplay
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.theme.CurrentSong
import com.example.musicplayer.ui.theme.MainScreen
import com.example.musicplayer.ui.theme.MusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MusicPlayerTheme {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen", builder = {
                        composable("MainScreen"){
                            MainScreen(modifier = Modifier, navController)
                        }
                        composable("CurrentSong/{songName}") { backStackEntry ->
                            val songName = backStackEntry.arguments?.getString("songName") ?: "Unknown"
                            CurrentSong(modifier = Modifier, songName = songName, artist = "artist")
                        }
                    })
            }
        }
    }
}

