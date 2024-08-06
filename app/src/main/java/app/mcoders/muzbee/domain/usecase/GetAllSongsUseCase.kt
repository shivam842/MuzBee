package app.mcoders.muzbee.domain.usecase

import app.mcoders.muzbee.data.models.Song
import app.mcoders.muzbee.data.repository_impl.MusicRepository
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(): List<Song> {
        return musicRepository.getAllSongs()
    }
}
