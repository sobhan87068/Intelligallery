package ir.sban.intelligallery.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import ir.sban.intelligallery.data.local.entity.MediaFileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaFileDao {
    @Query("SELECT * FROM media_files")
    fun getAllMediaFiles(): Flow<List<MediaFileEntity>>

    @Insert
    fun insertMediaFile(mediaFile: MediaFileEntity)

    @Upsert
    fun insertAllMediaFiles(mediaFileList: List<MediaFileEntity>)
}