package app.mcoders.muzbee.data.repository_impl

import android.net.Uri
import androidx.media3.common.MediaItem
import app.mcoders.muzbee.data.datasource.LocalDataSource
import app.mcoders.muzbee.data.datasource.RemoteDataSource
import app.mcoders.muzbee.data.models.Song
import app.mcoders.muzbee.data.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localDS: LocalDataSource,
    private val remoteDS: RemoteDataSource
) : MusicRepository {

    override fun getAllSongs(): List<Song> {

        // Fetch songs from data sources

        return emptyList()
    }

    override fun getMediaItem(): MediaItem {
        val uri = Uri.parse("file:///storage/emulated/0/Music/sample.mp3")
        return MediaItem.fromUri(uri)
    }
}