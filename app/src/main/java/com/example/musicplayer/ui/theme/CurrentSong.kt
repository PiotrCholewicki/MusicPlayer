package com.example.musicplayer.ui.theme

import android.media.MediaPlayer
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrentSong(
    modifier: Modifier = Modifier, // <- Zajmuje cały ekran,
    //mediaPlayer: MediaPlayer,
    songName: String,
    artist: String
) {
    Surface(
    modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = modifier.padding(8.dp).verticalScroll(rememberScrollState())
        )
        {
            Row(){
                Text("Cover Art")
            }
            Row(){
                Text(text = songName)
            }
            Row(){
                Text(text = artist)
            }
            Row(){

            }
        }
    }


}

//@Composable
//@Preview
//fun CurrentSong(
//
//)
