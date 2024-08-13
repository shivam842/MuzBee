package app.mcoders.muzbee.data.repository_impl

import android.util.Log
import app.mcoders.muzbee.data.datasource.LocalDataSource
import app.mcoders.muzbee.data.datasource.RemoteDataSource
import app.mcoders.muzbee.data.models.MusicFile
import app.mcoders.muzbee.data.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.measureTime

class MusicRepositoryImpl @Inject constructor(
    private val localDS: LocalDataSource,
    private val remoteDS: RemoteDataSource
) : MusicRepository {

    override suspend fun fetchMusicFiles(ids: List<String>): Flow<List<MusicFile>> {
        var list: Flow<List<MusicFile>>
        val time = measureTime {
            list = localDS.fetchMusicFiles(ids)
        }
        Log.e("TAG", "fetchMusicFiles: tile = $time" )
        return list
    }
}