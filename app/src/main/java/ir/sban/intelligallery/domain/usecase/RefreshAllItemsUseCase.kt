package ir.sban.intelligallery.domain.usecase

import android.content.Context
import ir.sban.intelligallery.domain.repository.MediaFileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshAllItemsUseCase @Inject constructor(
    private val mediaFileRepository: MediaFileRepository
) {
    suspend operator fun invoke(context: Context): Flow<Boolean> {
        return mediaFileRepository.refreshAllMediaFiles(context)
    }
}