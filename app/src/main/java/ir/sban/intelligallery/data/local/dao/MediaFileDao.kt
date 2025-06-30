package ir.sban.intelligallery.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import ir.sban.intelligallery.data.local.entity.MediaFileEntity
import ir.sban.intelligallery.data.local.entity.MediaFileWithMonthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaFileDao {
    @Query("SELECT * FROM media_files")
    fun getAllMediaFiles(): Flow<List<MediaFileEntity>>

    @Query(
        """
        SELECT *, strftime('%Y-%m', datetime(dateAdded, 'unixepoch')) AS month
        FROM media_files
        ORDER BY month DESC
    """
    )
    fun getGroupedMediaFiles(): Flow<List<MediaFileWithMonthEntity>>

    @Insert
    fun insertMediaFile(mediaFile: MediaFileEntity)

    @Upsert
    fun insertAllMediaFiles(mediaFileList: List<MediaFileEntity>)

    @Query("SELECT * FROM media_files WHERE mediaStoreId = :id")
    fun getMediaFileById(id: Long): Flow<MediaFileEntity>
}