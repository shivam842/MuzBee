package app.mcoders.muzbee.ui.main.composable

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.ui.PlayerView
import app.mcoders.muzbee.ui.main.viewmodel.MusicViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MusicPlayerScreen(viewModel: MusicViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val player by viewModel.playerState.collectAsState()
    val scaffoldState = remember { SnackbarHostState() }
    var musicFiles by remember { mutableStateOf<List<File>>(emptyList()) }

    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(android.Manifest.permission.READ_MEDIA_AUDIO)
    } else {
        listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    val permissionState = rememberMultiplePermissionsState(permissions)


    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    if (permissionState.allPermissionsGranted) {
        musicFiles = fetchMusicFiles(context)
        viewModel.initializePlayer(context, viewModel.song.value!!)
    } else {
        LaunchedEffect(scaffoldState) {
            scaffoldState.showSnackbar("Permission Denied. Unable to fetch music files.")
        }
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            if (player != null) {
                AndroidView(factory = { PlayerView(context).apply { this.player = player } })
            } else {
                Text(text = "Permission denied")
            }
        }
    }
}


fun fetchMusicFiles1(context: Context): List<File> {
    val musicFiles = mutableListOf<File>()
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(MediaStore.Audio.Media.DATA)

    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        val dataIndex = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        while (it.moveToNext()) {
            val filePath = it.getString(dataIndex)
            val file = File(filePath)
            if (file.exists()) {
                musicFiles.add(file)
            }
        }
    }
    return musicFiles
}