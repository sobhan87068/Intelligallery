package ir.sban.intelligallery.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface MediaFileRepository {
    suspend fun refreshAllMediaFiles(context: Context): Flow<Boolean>
}