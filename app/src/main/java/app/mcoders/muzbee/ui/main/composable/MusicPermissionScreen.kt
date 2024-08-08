package app.mcoders.muzbee.ui.main.composable

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import app.mcoders.muzbee.ui.main.viewmodel.MusicViewModel

@Composable
fun MusicPermissionScreen(viewModel: MusicViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val granted = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        granted.value = allGranted
        if (allGranted) {
            viewModel.loadMusicFiles()
        }
    }

    LaunchedEffect(Unit) {
        val permissionsToRequest = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.READ_MEDIA_AUDIO)
            }
        } else /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) */ {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            launcher.launch(permissionsToRequest.toTypedArray())
        } else {
            granted.value = true
            viewModel.loadMusicFiles()
        }
    }


    if (granted.value) {
        MusicScreen(viewModel)
    } else {
        Text("Permission denied")
    }
}
