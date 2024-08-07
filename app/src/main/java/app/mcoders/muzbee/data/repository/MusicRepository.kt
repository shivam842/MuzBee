package app.mcoders.muzbee.data.repository

import androidx.media3.common.MediaItem
import app.mcoders.muzbee.data.models.Song

interface MusicRepository {

    fun getAllSongs(): List<Song>

    fun getMediaItem(): MediaItem
}