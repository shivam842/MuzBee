package app.mcoders.muzbee.data.models

data class MusicFile(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val data: String,
    val duration: Long
)