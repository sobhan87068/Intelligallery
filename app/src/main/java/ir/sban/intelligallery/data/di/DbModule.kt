package ir.sban.intelligallery.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.sban.intelligallery.data.local.db.GalleryDatabase

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun provideDB(@ApplicationContext appContext: Context): GalleryDatabase = Room.databaseBuilder(
        appContext,
        GalleryDatabase::class.java,
        "intelliGallery"
    ).build()
}