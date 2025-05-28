
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicplayer.ui.theme.Footer
import com.example.musicplayer.viewmodel.MusicPlayerViewModel

@Composable
fun FilesDisplay(
    modifier: Modifier = Modifier,
    navController: NavController,
    musicPlayerViewModel: MusicPlayerViewModel
) {

    val context = LocalContext.current
    val assetManager = context.assets
    val audioFiles = assetManager.list("audio_files")?.filter { it.endsWith(".mp3") }

    val currentFile by musicPlayerViewModel.currentFile.collectAsState()


    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        audioFiles?.forEach { fileName ->
            val fileDisplayName = fileName.removeSuffix(".mp3")

            TextButton(
                onClick = {
                    musicPlayerViewModel.playFile(fileDisplayName)
                    navController.navigate("CurrentSong/$fileDisplayName")
                },
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = fileDisplayName)
                    if (currentFile == fileDisplayName) {
                        Text(text = " (Playing)", color = Color.Red)
                    }
                }
            }
        }

        if (currentFile != null) {
            Spacer(modifier = Modifier.weight(1f)) // wypycha slider na dół
            Footer(musicPlayerViewModel, navController)
        }
    }
}

