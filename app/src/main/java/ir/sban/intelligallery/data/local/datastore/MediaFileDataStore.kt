package ir.sban.intelligallery.data.local.datastore

import android.content.Context
import ir.sban.intelligallery.data.model.MediaFileDto
import kotlinx.coroutines.flow.Flow

interface MediaFileDataStore {
    suspend fun refreshMediaFiles(context: Context): Boolean

    fun getAllMediaFiles(): Flow<List<MediaFileDto>>

    fun getGroupedMediaFiles(): Flow<Map<String, List<MediaFileDto>>>

    fun getMediaFileById(id: Long): Flow<MediaFileDto>
}