package com.niyazismayilov.githubrepostats.data.local

import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CachedDataSource @Inject constructor(private var iCacheDao: CachedDao) : CachedRepository {
    override fun insert(cachedModel: CachedModel?): Completable? {
        return iCacheDao.insert(cachedModel)
    }

    override fun delete(cachedModel: CachedModel?): Completable? {
        return iCacheDao.delete(cachedModel)
    }

    override val allData: Flowable<List<CachedModel?>?>?
        get() = iCacheDao.allData
}