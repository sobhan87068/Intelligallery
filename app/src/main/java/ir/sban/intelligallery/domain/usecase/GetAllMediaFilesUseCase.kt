package ir.sban.intelligallery.domain.usecase

import ir.sban.intelligallery.domain.model.MediaFile
import ir.sban.intelligallery.domain.model.toPresentation
import ir.sban.intelligallery.domain.repository.MediaFileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMediaFilesUseCase @Inject constructor(
    private val mediaFileRepository: MediaFileRepository
) {
    operator fun invoke(): Flow<List<MediaFile>> {
        return mediaFileRepository.getAllMediaFiles().map { mediaFileDtos ->
            mediaFileDtos.map { it.toPresentation() }
        }
    }
}