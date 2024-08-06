package app.mcoders.muzbee.data.repository_impl

import app.mcoders.muzbee.data.models.Song

interface MusicRepository {

    fun getAllSongs(): List<Song>
}