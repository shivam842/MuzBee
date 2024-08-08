package app.mcoders.muzbee.data.repository

import app.mcoders.muzbee.data.models.MusicFile
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun fetchMusicFiles(): Flow<List<MusicFile>>
}