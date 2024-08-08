package app.mcoders.muzbee.ui.player.states

sealed class HomeUIState{
    object InitialHome: HomeUIState()
    object HomeReady: HomeUIState()
}