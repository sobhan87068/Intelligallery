package ir.sban.intelligallery.domain.repository

import android.content.Context
import ir.sban.intelligallery.data.model.MediaFileDto
import kotlinx.coroutines.flow.Flow

interface MediaFileRepository {
    suspend fun refreshAllMediaFiles(context: Context): Flow<Boolean>

    fun getAllMediaFiles(): Flow<List<MediaFileDto>>

    fun getGroupedMediaFiles(): Flow<Map<String, List<MediaFileDto>>>

    fun getMediaFileById(id: Long): Flow<MediaFileDto>
}