package com.niyazismayilov.githubrepostats.data

import com.niyazismayilov.githubrepostats.data.api.IApi
import com.niyazismayilov.githubrepostats.data.local.CachedRepository

interface IDataManager {
    val api: IApi
    val cachedRepo: CachedRepository
}