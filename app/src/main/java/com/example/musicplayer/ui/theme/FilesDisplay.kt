import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.example.musicplayer.ui.theme.CurrentSong
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FilesDisplay(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val assetManager = context.assets
    val audioFiles = assetManager.list("audio_files")?.filter { it.endsWith(".mp3") }

    var currentFile by remember { mutableStateOf<String?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // States for progress bar
    var currentPosition by remember { mutableStateOf(0) }
    var totalDuration by remember { mutableStateOf(0) }


    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        audioFiles?.forEach { fileName ->
            val fileDisplayName = fileName.removeSuffix(".mp3")

            TextButton(
                onClick = {
                    try {
                        // Zatrzymaj jeśli już coś gra
                        mediaPlayer?.stop()
                        mediaPlayer?.release()

                        // Nowy MediaPlayer
                        val afd: AssetFileDescriptor = assetManager.openFd("audio_files/$fileName")
                        val player = MediaPlayer()
                        player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                        player.prepare()
                        player.start()
                        isPlaying = true
                        totalDuration = player.duration

                        currentFile = fileDisplayName
                        mediaPlayer = player

                        // Zwolnij po zakończeniu
                        player.setOnCompletionListener {
                            it.release()
                            isPlaying = false
                            mediaPlayer = null
                            currentFile = null
                        }
                        navController.navigate("CurrentSong/$fileDisplayName")
                    } catch (e: Exception) {
                        Log.e("FilesDisplay", "Playback error: ${e.message}", e)
                    }
                },
                colors = ButtonDefaults.textButtonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
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
    }

    if(currentFile != null){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Teraz odtwarzane: ${currentFile ?: "N/A"}",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick ={
                    mediaPlayer?.let {
                        if(isPlaying){
                            it.pause()
                            isPlaying = false
                        }else{
                            it.start()
                            isPlaying = true
                        }
                    }
                }
            ) {
                Text(text = if(isPlaying) "Pause" else "Play")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick ={
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                    currentFile = null
                    isPlaying = false
                }
            ) {
                Text(text = "Stop")
            }
            Spacer(modifier = Modifier.width(8.dp))

            //update with each second
            LaunchedEffect(mediaPlayer, isPlaying) {
                while (isPlaying && mediaPlayer != null) {
                    delay(100L)
                    currentPosition = mediaPlayer?.currentPosition ?: 0
                }
            }

            Slider(
                value = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f,
                onValueChange = { newValue ->
                    // Calculate new position in milliseconds
                    val newPosition = (newValue * totalDuration).toInt()
                    mediaPlayer?.seekTo(newPosition)
                    currentPosition = newPosition // Update immediately for responsiveness


                },
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
                )
            )

        }
    }
}
