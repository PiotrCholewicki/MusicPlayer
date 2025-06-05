package com.example.musicplayer.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicplayer.viewmodel.MusicPlayerViewModel


@Composable
fun CurrentSong(
    modifier: Modifier = Modifier,
    songName: String,
    artist: String,
    musicPlayerViewModel: MusicPlayerViewModel,
    trackViewModel: TrackViewModel,
    navController: NavController,

) {

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //api display
            val viewModel: SongInfoViewModel = viewModel()

            LaunchedEffect(songName, artist) {
                viewModel.fetchSongInfo(
                    apiKey = "2a1e7bb8dbb77494cb8334cd8607ab0a",
                    songName = songName,
                    artist = artist

                )
            }
            val songInfo by viewModel.songInfo
            songInfo?.let { info ->
                Row { Text("${info.artist?.name} - ${info.name}") }

                Spacer(modifier.padding(4.dp))

                val minutes = info.duration.toInt()/1000/60
                val seconds = info.duration.toInt()/1000 - minutes * 60

                Row {Text("Długość: ${minutes}:${seconds}\r\n")}


                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(4.dp)){
                    AsyncImage(
                        model = info.album?.image?.get(3)?.url,
                        contentDescription = "Album cover",
                        modifier = modifier.fillMaxSize()
                    )
                }
                Row{
                    info.toptags?.tag?.forEach { tag -> Row{Text(text = "${tag.name} ")} } //displaying genres

                }


            }
            Spacer(modifier = Modifier.weight(1f))
            Row { Footer(musicPlayerViewModel, navController) }
        }

    }

}




