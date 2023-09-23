package com.niyazismayilov.githubrepostats.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedModel::class], version = 2, exportSchema = false)
abstract class CachedDatabase : RoomDatabase() {
    abstract val cacheDao: CachedDao?
}