package com.example.musicplayer.ui.theme

import FilesDisplay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
        .fillMaxWidth()

) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        //later to be introduced
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("TODO: RASPBERRY")
        }

        //List of audio tracks
        Row() {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(100.dp)
            ) {
                Text(stringResource(R.string.list_of_audio_tracks))
                FilesDisplay()

            }

        }
        Spacer(modifier = Modifier.weight(1f))
        //Audio player bar (play, resume etc.)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,

            ) {
            Text("Audio bar")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MusicPlayerTheme {
        Modifier.fillMaxSize()
        MainScreen()
    }
}