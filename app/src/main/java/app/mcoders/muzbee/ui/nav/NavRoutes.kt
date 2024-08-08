package app.mcoders.muzbee.ui.nav

import kotlinx.serialization.Serializable

sealed class NavRoutes{

    @Serializable
    object Main

    @Serializable
    object Player
}