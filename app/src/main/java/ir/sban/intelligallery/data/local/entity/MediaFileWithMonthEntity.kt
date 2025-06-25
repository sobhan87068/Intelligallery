package ir.sban.intelligallery.data.local.entity

import androidx.room.Embedded

data class MediaFileWithMonthEntity(
    @Embedded val mediaFileEntity: MediaFileEntity,
    val month: String
)