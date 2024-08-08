package app.mcoders.muzbee.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import app.mcoders.muzbee.ui.theme.MuzBeeTheme
import app.mcoders.muzbee.ui.nav.NavGraph
import app.mcoders.muzbee.ui.player.PlayerViewModel
import app.mcoders.muzbee.ui.player.service.MediaPlaybackService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val playerViewModel: PlayerViewModel by viewModels()
    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            MuzBeeTheme {
                PermissionAndLaunch(playerViewModel = playerViewModel, startMusicServiceCallback = { startMusicService() })
            }
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    private fun startMusicService() {
        if (!isServiceRunning) {
            val intent = Intent(this, MediaPlaybackService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
            isServiceRunning = true
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionAndLaunch(
    playerViewModel: PlayerViewModel,
    startMusicServiceCallback: () -> Unit
) {

    val isPermissionGranted = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= 33)
            Manifest.permission.READ_MEDIA_AUDIO
        else
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val lifeCycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifeCycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isPermissionGranted.launchPermissionRequest()
            }
        }

        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavGraph(
            navController = rememberNavController(),
            playerViewModel = playerViewModel,
            startMusicServiceCallback = startMusicServiceCallback
        )
    }
}
