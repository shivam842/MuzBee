package app.mcoders.muzbee.data.repository_impl

import app.mcoders.muzbee.data.datasource.LocalDataSource
import app.mcoders.muzbee.data.datasource.RemoteDataSource
import app.mcoders.muzbee.data.models.MusicFile
import app.mcoders.muzbee.data.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localDS: LocalDataSource,
    private val remoteDS: RemoteDataSource
) : MusicRepository {

    override suspend fun fetchMusicFiles(): Flow<List<MusicFile>> {
        return localDS.fetchMusicFiles()
    }
}