import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicplayer.ui.screen.Footer
import com.example.musicplayer.viewmodel.TrackViewModel
import com.example.musicplayer.viewmodel.MusicPlayerViewModel
import androidx.compose.runtime.livedata.observeAsState
@Composable
fun FilesDisplay(
    modifier: Modifier = Modifier,
    navController: NavController,
    musicPlayerViewModel: MusicPlayerViewModel,
    trackViewModel: TrackViewModel
) {

    val context = LocalContext.current
    val assetManager = context.assets
    val audioFiles = assetManager.list("audio_files")?.filter { it.endsWith(".mp3") }

    val currentFile by musicPlayerViewModel.currentFile.collectAsState()
//    val trackViewModel by viewModels {
//        TrackViewModel((application as MusicPlayerApplication).repository)
//    }
//    val repository = MusicPlayerApplication.repository
//    val factory = TrackModelFactory(repository)
//    trackViewModel = ViewModelProvider(this, factory)[TrackViewModel::class.java]

//     Add data if database is empty
//    trackViewModel.tracks.observe(this) { trackList ->
//        if (trackList.isEmpty()) {
//            trackViewModel.sampleTracks.forEach { sampleTrack ->
//                trackViewModel.addTrack(sampleTrack)
//            }
//        } else {
//            trackList.forEach { track ->
//                Log.d(
//                    "MainActivity",
//                    "Track: id=${track.id}, name=${track.name}, artist=${track.artist}, path=${track.path}"
//                )
//            }
//        }
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val tracks by trackViewModel.tracks.observeAsState(emptyList())
        tracks.forEach { track ->
            val trackTitle = track.name
            val trackArtist = track.artist

            TextButton(
                onClick = {
                    musicPlayerViewModel.playFile(track.path)
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



