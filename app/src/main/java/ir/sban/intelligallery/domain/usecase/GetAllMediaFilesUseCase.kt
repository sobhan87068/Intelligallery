package ir.sban.intelligallery.domain.usecase

import ir.sban.intelligallery.domain.model.MediaFile
import ir.sban.intelligallery.domain.model.toPresentation
import ir.sban.intelligallery.domain.repository.MediaFileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class GetAllMediaFilesUseCase @Inject constructor(
    private val mediaFileRepository: MediaFileRepository
) {
    operator fun invoke(): Flow<Map<String, List<MediaFile>>> {
        return mediaFileRepository.getGroupedMediaFiles().map { map ->
            map.mapKeys { (key, _) ->
                formatMonthYear(key)
            }.mapValues { (_, mediaFileDtos) ->
                mediaFileDtos.map { it.toPresentation() }
            }
        }
    }

    private fun formatMonthYear(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(input)!!
        return outputFormat.format(date)
    }
}