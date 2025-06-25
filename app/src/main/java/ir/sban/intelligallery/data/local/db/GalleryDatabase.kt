package ir.sban.intelligallery.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.sban.intelligallery.data.local.dao.MediaFileDao
import ir.sban.intelligallery.data.local.entity.MediaFileEntity

@Database(entities = [MediaFileEntity::class], version = 1, exportSchema = false)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun getMediaFileDao(): MediaFileDao
}