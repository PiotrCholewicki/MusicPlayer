package com.example.musicplayer.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.viewmodel.SongInfoViewModel
import com.example.musicplayer.viewmodel.TrackViewModel
import com.example.musicplayer.viewmodel.MusicPlayerViewModel


@Composable
fun CurrentSong(
    modifier: Modifier = Modifier,
    songName: String,
    artist: String,
    musicPlayerViewModel: MusicPlayerViewModel,
    navController: NavController,

    ) {

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //api display
            val apiKey = stringResource(R.string.api_key)
            val viewModel: SongInfoViewModel = viewModel()

            LaunchedEffect(songName, artist, apiKey) {
                viewModel.fetchSongInfo(
                    apiKey = apiKey,
                    songName = songName,
                    artist = artist

                )
            }

            val songInfo by viewModel.songInfo
            songInfo?.let { info ->
                Row(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "${info.artist?.name} - ${info.name}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row {
                    Text("Genres: ", fontWeight = FontWeight.SemiBold)
                    info.toptags?.tag?.forEach { tag ->
                        if (tag.name != info.artist?.name) {
                            Row { Text(text = "${tag.name} ") }
                        } //displaying genres without duplicates with the band name
                    }
                }
                Spacer(modifier.padding(8.dp))

                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(4.dp)) {
                    AsyncImage(
                        model = info.album?.image?.get(3)?.url,
                        contentDescription = "Album cover",
                        modifier = modifier
                            .fillMaxSize()
                            .size(240.dp)
                    )
                }

                Spacer(modifier.padding(8.dp))

                val minutes = info.duration.toInt() / 1000 / 60
                val seconds = info.duration.toInt() / 1000 - minutes * 60

                Row {
                    Text("Length: ", fontWeight = FontWeight.SemiBold )
                    Text("${minutes}:${seconds}")
                }


            }
            Spacer(modifier = Modifier.weight(1f))
            Row { Footer(musicPlayerViewModel, navController) }
        }

    }

}




