package com.example.musicplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.database.Track
import com.example.musicplayer.data.repository.TrackRepository
import kotlinx.coroutines.launch

class TrackViewModel(private val repository: TrackRepository) : ViewModel() {
    var tracks: LiveData<List<Track>>  = repository.allTracks.asLiveData()

    fun addTrack(newTrack: Track) = viewModelScope.launch {
        repository.insertTrack(newTrack)
    }

    fun updateTrack(newTrack: Track) = viewModelScope.launch {
        repository.updateTrack(newTrack)
    }
    fun getTrackById(id: Int) = viewModelScope.launch {
        repository.getTrackById(id)
    }

    fun deleteTrack(track: Track) = viewModelScope.launch {
        repository.deleteTrack(track)
    }
}
class TrackModelFactory(private val repository: TrackRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TrackViewModel::class.java))
            return TrackViewModel(repository) as T
        throw IllegalArgumentException("Unknown Class for ViewModel")
    }

}