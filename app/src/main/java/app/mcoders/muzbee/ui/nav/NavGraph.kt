package app.mcoders.muzbee.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mcoders.muzbee.ui.main.composable.HomeScreen
import app.mcoders.muzbee.ui.player.PlayerScreen
import app.mcoders.muzbee.ui.player.PlayerViewModel
import app.mcoders.muzbee.ui.player.states.HomeUiEvents

@Composable
fun NavGraph(
    navController: NavHostController,
    playerViewModel: PlayerViewModel,
    startMusicServiceCallback: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main
    ) {

        composable<NavRoutes.Main> {
            HomeScreen(
                progress = playerViewModel.progress,
                onProgressCallback = {
                    playerViewModel.onHomeUiEvents(HomeUiEvents.SeekTo(it))
                },
                isMusicPlaying = playerViewModel.isMusicPlaying,
                currentPlayingMusic = playerViewModel.currentSelectedMusic,
                musicList = playerViewModel.musicList,
                onStartCallback = {
                    playerViewModel.onHomeUiEvents(HomeUiEvents.PlayPause)
                },
                onMusicClick = {
                    playerViewModel.onHomeUiEvents(HomeUiEvents.CurrentAudioChanged(it))
                    startMusicServiceCallback.invoke()
                },
                onNextCallback = {
                    playerViewModel.onHomeUiEvents(HomeUiEvents.SeekToNext)
                },
                onMiniPlayerClickCallback = {
                    navController.navigate(NavRoutes.Player)
                }
            )
        }
        composable<NavRoutes.Player> {
            PlayerScreen(playerViewModel)
        }
    }
}
