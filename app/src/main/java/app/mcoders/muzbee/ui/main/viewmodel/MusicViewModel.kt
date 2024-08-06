package app.mcoders.muzbee.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import app.mcoders.muzbee.data.models.Song
import app.mcoders.muzbee.domain.usecase.GetAllSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase
) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    fun fetchSongs() {
        _songs.value = getAllSongsUseCase()
    }
}
