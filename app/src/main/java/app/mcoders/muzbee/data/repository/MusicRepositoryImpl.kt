package app.mcoders.muzbee.data.repository

import app.mcoders.muzbee.data.datasource.LocalDataSource
import app.mcoders.muzbee.data.datasource.RemoteDataSource
import app.mcoders.muzbee.data.models.Song
import app.mcoders.muzbee.data.repository_impl.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localDS: LocalDataSource,
    private val remoteDS: RemoteDataSource
) : MusicRepository {

    override fun getAllSongs(): List<Song> {

        // Fetch songs from data sources

        return emptyList()
    }

}