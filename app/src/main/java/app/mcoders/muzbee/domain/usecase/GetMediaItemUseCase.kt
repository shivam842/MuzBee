package app.mcoders.muzbee.domain.usecase

import androidx.media3.common.MediaItem
import app.mcoders.muzbee.data.repository.MusicRepository
import javax.inject.Inject

class GetMediaItemUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(): MediaItem {
        return musicRepository.getMediaItem()
    }
}