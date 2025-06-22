package ir.sban.intelligallery.data.local.datastore

import android.content.Context

interface MediaFileDataStore {
    suspend fun refreshMediaFiles(context: Context): Boolean
}