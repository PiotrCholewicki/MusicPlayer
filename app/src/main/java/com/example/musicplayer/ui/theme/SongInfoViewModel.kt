package com.example.musicplayer.ui.theme


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicplayer.model.Track
import com.example.musicplayer.model.SongInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SongInfoViewModel : ViewModel() {
    private val _songInfo = mutableStateOf<Track?>(null)
    val songInfo: State<Track?> = _songInfo

    fun fetchSongInfo(apiKey: String, artist: String, songName: String) {
        val apiService = RetrofitClient.apiServiceInstance
        val call = apiService.getSongInfo(apiKey, artist, songName)
        call.enqueue(object : Callback<SongInfoResponse> {
            override fun onResponse(call: Call<SongInfoResponse>, response: Response<SongInfoResponse>) {
                if(response.isSuccessful){
                    val trackData = response.body()?.track
                    if (trackData != null){
                        _songInfo.value = trackData
                        Log.d("SongInfoViewModel", "Song info fetched: ${trackData.name}")
                    }
                    else{
                        Log.e("SongInfoViewModel", "API response unsuccessful: ${response.errorBody()?.string()}")
                    }
                }
            }
            override fun onFailure(call: Call<SongInfoResponse>, t: Throwable){
                Log.e("SongInfoViewModel", "API call failed: ${t.message}")
            }
        })
    }
}