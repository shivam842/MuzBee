package app.mcoders.muzbee.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import app.mcoders.muzbee.data.models.Song
import app.mcoders.muzbee.domain.usecase.GetAllSongsUseCase
import app.mcoders.muzbee.domain.usecase.GetMediaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val getMediaItemUseCase: GetMediaItemUseCase
) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _song = MutableStateFlow<MediaItem?>(null)
    val song: StateFlow<MediaItem?> = _song


    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState


    fun fetchSongs() {
        _songs.value = getAllSongsUseCase()
    }

    fun fetchMediaItem() {
        _song.value = getMediaItemUseCase()
    }


    fun initializePlayer(context: Context, mediaItem: MediaItem) {
        viewModelScope.launch {
            val player = ExoPlayer.Builder(context).build().apply {
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
            _playerState.value = player
        }
    }

    override fun onCleared() {
        super.onCleared()
        _playerState.value?.release()
    }

}
