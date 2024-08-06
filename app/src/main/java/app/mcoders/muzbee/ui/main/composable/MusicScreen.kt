@file:OptIn(ExperimentalMaterial3Api::class)

package app.mcoders.muzbee.ui.main.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.mcoders.muzbee.data.models.Song
import app.mcoders.muzbee.ui.main.viewmodel.MusicViewModel

@Composable
fun MusicScreen(viewModel: MusicViewModel = hiltViewModel()) {
    val songs by viewModel.songs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Music Player") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(songs) { song ->
                SongItem(song)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchSongs()
    }
}

@Composable
fun SongItem(song: Song) {
    Text(text = song.title, style = MaterialTheme.typography.headlineLarge)
}
