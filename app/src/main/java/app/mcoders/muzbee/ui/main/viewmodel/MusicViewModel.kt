package app.mcoders.muzbee.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mcoders.muzbee.data.models.MusicFile
import app.mcoders.muzbee.domain.usecase.GetAllSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
) : ViewModel() {

    private val _songs = MutableStateFlow<List<MusicFile>>(emptyList())
    val songs: StateFlow<List<MusicFile>> = _songs

    fun loadMusicFiles() {
        viewModelScope.launch {
            getAllSongsUseCase().collectLatest { files ->
                _songs.value = files
            }
        }
    }
}
