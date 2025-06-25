package ir.sban.intelligallery.data.repository

import android.content.Context
import ir.sban.intelligallery.data.local.datastore.MediaFileDataStore
import ir.sban.intelligallery.data.model.MediaFileDto
import ir.sban.intelligallery.domain.repository.MediaFileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaFileRepositoryImpl @Inject constructor(
    private val mediaFileDataStore: MediaFileDataStore
) : MediaFileRepository {

    override suspend fun refreshAllMediaFiles(context: Context): Flow<Boolean> {
        return flow {
            emit(mediaFileDataStore.refreshMediaFiles(context))
        }
    }

    override fun getAllMediaFiles(): Flow<List<MediaFileDto>> {
        return mediaFileDataStore.getAllMediaFiles()
    }
}