package app.mcoders.muzbee.ui.main.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import app.mcoders.muzbee.data.models.MusicFile
import app.mcoders.muzbee.ui.player.composable.MiniPlayer
import app.mcoders.muzbee.ui.player.composable.MusicItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    progress: Float,
    onProgressCallback: (Float) -> Unit,
    isMusicPlaying: Boolean,
    currentPlayingMusic: MusicFile,
    musicList: List<MusicFile>,
    onStartCallback: () -> Unit,
    onMusicClick: (Int) -> Unit,
    onNextCallback: () -> Unit,
    onMiniPlayerClickCallback: (musicItem: MusicFile) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = { Text(text = "Music App") }
            )
        },
        bottomBar = {
            MiniPlayer(
                musicItem = currentPlayingMusic,
                progress = progress,
                isMusicPlaying = isMusicPlaying,
                onProgressCallback = onProgressCallback,
                onNextCallback = onNextCallback,
                onStartCallback = onStartCallback,
                onMiniPlayerClick = onMiniPlayerClickCallback
            )
        }
    ) {

        LazyColumn(
            contentPadding = it,
        ) {
            items(
                items = musicList,
                key = { item ->
                    item.id
                }
            ) { item ->
                MusicItem(item, onClickCallback = {
                    onMusicClick(musicList.indexOf(item))
                })
            }
        }
    }
}


