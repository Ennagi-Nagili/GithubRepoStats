package com.niyazismayilov.githubrepostats.di.module

import android.app.Application
import androidx.room.Room
import com.niyazismayilov.githubrepostats.data.local.CachedDao
import com.niyazismayilov.githubrepostats.data.local.CachedDataSource
import com.niyazismayilov.githubrepostats.data.local.CachedDatabase
import com.niyazismayilov.githubrepostats.data.local.CachedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideCacheDataBase(application: Application?): CachedDatabase {
        return Room
            .databaseBuilder(application!!, CachedDatabase::class.java, "GithabStats_db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCacheDao(cacheDatabase: CachedDatabase): CachedDao? {
        return cacheDatabase.cacheDao
    }

    @Singleton
    @Provides
    fun provideCacheRepository(iCacheDao: CachedDao?): CachedRepository {
        return CachedDataSource(iCacheDao!!)
    }
}