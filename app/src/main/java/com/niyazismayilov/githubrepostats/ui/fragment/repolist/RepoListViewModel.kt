package com.niyazismayilov.githubrepostats.ui.fragment.repolist

import androidx.lifecycle.MutableLiveData
import com.niyazismayilov.githubrepostats.data.IDataManager
import com.niyazismayilov.githubrepostats.data.Resource
import com.niyazismayilov.githubrepostats.data.local.CachedModel
import com.niyazismayilov.githubrepostats.data.model.request.RepoListRequest
import com.niyazismayilov.githubrepostats.data.model.response.RepoItem
import com.niyazismayilov.githubrepostats.data.model.response.RepoListResponse
import com.niyazismayilov.githubrepostats.ui.base.BaseViewModel
import com.niyazismayilov.githubrepostats.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private var iDataManager: IDataManager) : BaseViewModel() {
    var repoListResponseMutableLiveData = MutableLiveData<Resource<List<RepoItem>?>>()
    var cachedList: MutableList<RepoItem?> = ArrayList()
    var searchList: MutableList<RepoItem?> = ArrayList()
    fun getRepoList(repoListRequest: RepoListRequest?) {
        repoListResponseMutableLiveData.postValue(Resource.Companion.loading<List<RepoItem>?>())
        compositeDisposable.add(
            iDataManager.api.getRepoList(
                repoListRequest!!.createdAt,
                repoListRequest.sort,
                repoListRequest.order,
                repoListRequest.page
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: RepoListResponse? ->
                    repoListResponseMutableLiveData.postValue(
                        Resource.success(
                            response!!.repoItemList
                        )
                    )
                    cachedList.addAll(response.repoItemList!!)
                    searchList.addAll(response.repoItemList)
                }) { throwable: Throwable? ->
                    repoListResponseMutableLiveData.postValue(
                        Resource.error<List<RepoItem>?>(
                            throwable
                        )
                    )
                })
    }

    fun filterByQuery(text: String?) {
        val list: MutableList<RepoItem?> = ArrayList()
        for (repoItem in searchList) {
            if (repoItem!!.name!!.contains(text!!)) {
                list.add(repoItem)
            }
        }
        cachedList.clear()
        cachedList.addAll(list)
        repoListResponseMutableLiveData.postValue(
            Resource.success(
                cachedList as List<RepoItem>
            )
        )
    }

    fun addToFavorites(item: RepoItem) {
        compositeDisposable.add(
            iDataManager.cachedRepo.insert(
                CachedModel(
                    item.description,
                    item.name,
                    item.stars,
                    item.repoOwner.avatar_url,
                    item.language,
                    item.forks,
                    item.created_at,
                    item.repoOwner.html_url,
                    item.repoOwner.login
                )
            )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun getLastDateTime(type: String?): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        when (type) {
            Constants.DAY -> cal.add(Calendar.DATE, -1)
            Constants.MONTHLY -> cal.add(Calendar.MONTH, -1)
            Constants.WEEKLY -> cal.add(Calendar.DAY_OF_WEEK, -1)
        }
        return StringBuilder().append("created:").append(format.format(cal.time)).toString()
    }

    fun clearList() {
        cachedList.clear()
    }
}