package app.mcoders.muzbee.data.datasource

import app.mcoders.muzbee.data.models.MusicFile
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
   suspend fun fetchMusicFiles(ids: List<String>): Flow<List<MusicFile>>
}
