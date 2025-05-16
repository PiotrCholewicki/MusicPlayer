import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text

@Composable
fun FilesDisplay(){
    /**
     * accessing the audio files in folder assets/audio_files
     */
    val assetManager = LocalContext.current.assets
    val audioFiles = assetManager.list("audio_files")

    audioFiles?.forEach{ fileName ->
        //TODO: filtering only mp3 files
        val noExtension = fileName.dropLast(4)
        //TODO: convert into database instead of xml
        Text(text = noExtension)
    }
}