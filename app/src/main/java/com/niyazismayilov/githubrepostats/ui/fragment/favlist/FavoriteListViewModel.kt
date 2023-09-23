package com.niyazismayilov.githubrepostats.ui.fragment.favlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.niyazismayilov.githubrepostats.data.IDataManager
import com.niyazismayilov.githubrepostats.data.Resource
import com.niyazismayilov.githubrepostats.data.local.CachedModel
import com.niyazismayilov.githubrepostats.data.model.response.RepoItem
import com.niyazismayilov.githubrepostats.data.model.response.RepoOwner
import com.niyazismayilov.githubrepostats.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.stream.Collectors
import javax.inject.Inject

class FavoriteListViewModel @Inject constructor(var iDataManager: IDataManager) : BaseViewModel() {
    var cachedModelList: List<CachedModel?>? = null
    var repoListResponseMutableLiveData = MutableLiveData<Resource<List<RepoItem>>>()

    @get:RequiresApi(api = Build.VERSION_CODES.N)
    val favList: Unit
        get() {
            compositeDisposable.add(
                iDataManager.cachedRepo.allData!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response: List<CachedModel?>? ->
                        cachedModelList = response
                        repoListResponseMutableLiveData.postValue(
                            Resource.success(
                                convertToDao(response)
                            )
                        )
                    }) { throwable: Throwable? ->
                        repoListResponseMutableLiveData.postValue(
                            Resource.error(throwable)
                        )
                    })
        }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun convertToDao(response: List<CachedModel?>?): List<RepoItem> {
        return response!!.stream().map { item: CachedModel? ->
            RepoItem(
                item!!.id,
                item.description,
                item.name,
                item.stargazers_count,
                item.language,
                item.forks,
                item.created_at,
                RepoOwner(item.avatar_url, item.login, item.html_url)
            )
        }
            .collect(Collectors.toList())
    }

    fun removeFav(model: RepoItem) {
        compositeDisposable.add(
            iDataManager.cachedRepo.delete(getCachedModel(model))!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun getCachedModel(repoItem: RepoItem): CachedModel? {
        for (item in cachedModelList!!) {
            if (item!!.id == repoItem.id) return item
        }
        return null
    }
}