package com.example.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.musicplayer.ui.navigation.AppNavigation
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.viewmodel.TrackModelFactory
import com.example.musicplayer.viewmodel.TrackViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val repository = (application as MusicPlayerApplication).repository
        val trackViewModel: TrackViewModel by viewModels {
            TrackModelFactory(repository)
        }

        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {
                AppNavigation(trackViewModel)
            }
        }
        //enableEdgeToEdge()
    }

}


