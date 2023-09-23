package com.niyazismayilov.githubrepostats.data.local

import io.reactivex.Completable
import io.reactivex.Flowable

interface CachedRepository {
    fun insert(cachedModel: CachedModel?): Completable?
    fun delete(cachedModel: CachedModel?): Completable?
    val allData: Flowable<List<CachedModel?>?>?
}