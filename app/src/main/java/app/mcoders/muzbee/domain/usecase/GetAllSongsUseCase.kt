package app.mcoders.muzbee.domain.usecase

import app.mcoders.muzbee.data.models.MusicFile
import app.mcoders.muzbee.data.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(): Flow<List<MusicFile>> {
        return musicRepository.fetchMusicFiles()
    }
}
