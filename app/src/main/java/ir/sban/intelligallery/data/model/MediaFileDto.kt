package ir.sban.intelligallery.data.model

import ir.sban.intelligallery.data.local.entity.MediaFileEntity

data class MediaFileDto(
    val mediaStoreId: Long,
    val contentUri: String,
    val displayName: String,
    val dateAdded: Long, // timestamp in milliseconds
    val dateModified: Long, // timestamp in milliseconds
    val mimeType: String,
    val width: Int,
    val height: Int,
    val duration: Long? = null, // Only for videos (milliseconds)
    val size: Long, // Size in bytes
    val isVideo: Boolean
)

fun MediaFileEntity.toDto() = MediaFileDto(
    mediaStoreId = mediaStoreId,
    contentUri = contentUri,
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