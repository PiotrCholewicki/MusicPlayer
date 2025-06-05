package com.example.musicplayer.viewmodel

import android.app.Application
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MusicPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val assetManager = context.assets

    private var mediaPlayer: MediaPlayer? = null

    private val _currentFile = MutableStateFlow<String?>(null)
    val currentFile = _currentFile.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    private val _totalDuration = MutableStateFlow(0)
    val totalDuration = _totalDuration.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(100L)
                mediaPlayer?.let {
                    if (_isPlaying.value) {
                        _currentPosition.value = it.currentPosition
                    }
                }
            }
        }
    }

    fun playFile(fileName: String) {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()

            val afd: AssetFileDescriptor = assetManager.openFd(fileName)
            val player = MediaPlayer()
            player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            player.prepare()
            player.start()

            _totalDuration.value = player.duration
            _currentFile.value = fileName
            _isPlaying.value = true
            mediaPlayer = player

            player.setOnCompletionListener {
                it.release()
                _isPlaying.value = false
                _currentFile.value = null
                mediaPlayer = null
            }

        } catch (e: Exception) {
            Log.e("ViewModel", "Playback error: ${e.message}", e)
        }
    }

    fun togglePlayPause() {
        mediaPlayer?.let {
            if (_isPlaying.value) {
                it.pause()
                _isPlaying.value = false
            } else {
                it.start()
                _isPlaying.value = true
            }
        }
    }

    fun stopPlayback() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlaying.value = false
        _currentFile.value = null
        _currentPosition.value = 0
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
        _currentPosition.value = position
    }
}
