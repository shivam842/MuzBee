package app.mcoders.muzbee.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mcoders.muzbee.ui.main.composable.MusicPermissionAndFetch
import app.mcoders.muzbee.ui.main.composable.MusicPlayerScreen
import app.mcoders.muzbee.ui.main.composable.MusicScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavRoutes.Main) {
        composable<NavRoutes.Main> {
            //MusicScreen()
            //MusicPermissionAndFetch()
            MusicPlayerScreen()
            /*HomeScreen(
                navigateToProfile = {
                    navController.navigate(NavRoutes.Profile.path)
                },
                navigateToDetails = { id ->
                    navController.navigate(NavRoutes.Details.withArgs(id))
                },
                exitApp = { navController.popBackStack() },
            )*/
        }

        /*composable(NavRoutes.Details.path) {
            DetailsScreen(
                onBackPressed = { navController.popBackStack() },
            )
        }

        composable(NavRoutes.Profile.path) {
            ProfileScreen(
                onBackPressed = { navController.popBackStack() },
            )
        }*/
    }
}
