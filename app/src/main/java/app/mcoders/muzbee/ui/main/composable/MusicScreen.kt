@file:OptIn(ExperimentalMaterial3Api::class)

package app.mcoders.muzbee.ui.main.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import app.mcoders.muzbee.ui.main.viewmodel.MusicViewModel

@Composable
fun MusicScreen(viewModel: MusicViewModel = hiltViewModel()) {
    val songs by viewModel.songs.collectAsState()

    LazyColumn {
        items(songs) { musicFile ->
            Text(text = "${musicFile.title} - ${musicFile.artist}")
        }
    }
}
