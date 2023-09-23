package com.niyazismayilov.githubrepostats.data

import com.niyazismayilov.githubrepostats.data.api.IApi
import com.niyazismayilov.githubrepostats.data.local.CachedRepository
import javax.inject.Inject

class AppDataManager @Inject constructor(
    override val api: IApi,
    override val cachedRepo: CachedRepository
) : IDataManager