package ir.sban.intelligallery.data.local.datastore

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import ir.sban.intelligallery.data.local.db.GalleryDatabase
import ir.sban.intelligallery.data.local.entity.MediaFileEntity
import ir.sban.intelligallery.data.model.MediaFileDto
import ir.sban.intelligallery.data.model.toDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaFileDataStoreImpl @Inject constructor(
    private val galleryDatabase: GalleryDatabase
) : MediaFileDataStore {
    override suspend fun refreshMediaFiles(context: Context): Boolean {
        val result = withContext(Dispatchers.IO) { // Ensure this runs on an IO thread
            val mediaList = mutableListOf<MediaFileEntity>()

            // Define the columns you want to retrieve
            val projection = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.WIDTH,
                MediaStore.Files.FileColumns.HEIGHT,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Video.Media.DURATION // Specific to videos, might be null for images
            )

            // Selection criteria: only images and videos
            val selection =
                "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE} = ?"
            val selectionArgs = arrayOf(
                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
            )

            // Sort order: newest first
            val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

            val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // In Android 10 (Q) and above, MediaStore.Files.getContentUri requires a volume name.
                // "external" refers to the primary shared storage.
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                // For older Android versions, use the generic content URI for external files.
                MediaStore.Files.getContentUri("external")
            }


            context.contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                val dateAddedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)
                val dateModifiedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)
                val mediaTypeColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE)
                val mimeTypeColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
                val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.WIDTH)
                val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.HEIGHT)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
                // Duration column might not exist for images, so get its index carefully
                val durationColumn = cursor.getColumnIndex(MediaStore.Video.Media.DURATION)


                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getString(nameColumn)
                    val dateAdded = cursor.getLong(dateAddedColumn)
                    val dateModified = cursor.getLong(dateModifiedColumn)
                    val mediaType = cursor.getInt(mediaTypeColumn)
                    val mimeType = cursor.getString(mimeTypeColumn)
                    val width = cursor.getInt(widthColumn)
                    val height = cursor.getInt(heightColumn)
                    val size = cursor.getLong(sizeColumn)

                    val isVideo = mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
                    val duration =
                        if (isVideo && durationColumn != -1) cursor.getLong(durationColumn) else null

                    // Construct the correct content URI for the item
                    val contentUri: Uri = when (mediaType) {
                        MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE -> {
                            ContentUris.withAppendedId(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                id
                            )
                        }

                        MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO -> {
                            ContentUris.withAppendedId(
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                id
                            )
                        }

                        else -> {
                            // This case should ideally not be reached due to the selection filter,
                            // but it's good practice to handle unexpected media types.
                            Log.w(
                                "MediaStore",
                                "Unknown media type encountered: $mediaType for ID $id"
                            )
                            continue
                        }
                    }

                    // Add to list
                    mediaList.add(
                        MediaFileEntity(
                            mediaStoreId = id,
                            contentUri = contentUri.toString(), // Store as String in Room
                            displayName = displayName,
                            dateAdded = dateAdded,
                            dateModified = dateModified,
                            mimeType = mimeType,
                            width = width,
                            height = height,
                            duration = duration,
                            size = size,
                            isVideo = isVideo
                        )
                    )
                }
            }
            galleryDatabase.getMediaFileDao().insertAllMediaFiles(mediaList)
            true
        }
        return result
    }

    override fun getAllMediaFiles(): Flow<List<MediaFileDto>> =
        galleryDatabase.getMediaFileDao().getAllMediaFiles().map { entities ->
            entities.map { it.toDto() }
        }
}