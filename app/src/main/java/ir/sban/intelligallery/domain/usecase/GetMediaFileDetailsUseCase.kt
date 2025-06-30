package ir.sban.intelligallery.domain.usecase

import ir.sban.intelligallery.domain.model.toPresentation
import ir.sban.intelligallery.domain.repository.MediaFileRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMediaFileDetailsUseCase @Inject constructor(
    private val mediaFileRepository: MediaFileRepository
) {
    operator fun invoke(id: Long) =
        mediaFileRepository.getMediaFileById(id).map { it.toPresentation() }
}