package ir.sban.intelligallery.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_files")
data class MediaFileEntity(
    @PrimaryKey(autoGenerate = false) // Use MediaStore ID as primary key
    val mediaStoreId: Long,
    val contentUri: String, // Store Uri as string
    val displayName: String,
    val dateAdded: Long, // Unix timestamp in seconds
    val dateModified: Long, // Unix timestamp in seconds
    val mimeType: String,
    val width: Int,
    val height: Int,
    val duration: Long? = null, // Only for videos (milliseconds)
    val size: Long, // Size in bytes
    val isVideo: Boolean
)