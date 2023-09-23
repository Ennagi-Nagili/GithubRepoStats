package com.niyazismayilov.githubrepostats.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niyazismayilov.githubrepostats.utils.Constants
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CachedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cachedModel: CachedModel?): Completable?

    @Delete
    fun delete(cachedModel: CachedModel?): Completable?

    @get:Query("SELECT * FROM " + Constants.TABLE_FAVS)
    val allData: Flowable<List<CachedModel?>?>?
}