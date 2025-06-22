package ir.sban.intelligallery.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sban.intelligallery.data.local.datastore.MediaFileDataStore
import ir.sban.intelligallery.data.local.datastore.MediaFileDataStoreImpl
import ir.sban.intelligallery.data.repository.MediaFileRepositoryImpl
import ir.sban.intelligallery.domain.repository.MediaFileRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindRepository(mediaFileRepositoryImpl: MediaFileRepositoryImpl): MediaFileRepository

    @Binds
    fun bindDataSource(mediaFileDataStoreImpl: MediaFileDataStoreImpl): MediaFileDataStore
}