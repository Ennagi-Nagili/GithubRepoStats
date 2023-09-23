package com.niyazismayilov.githubrepostats.data.api

import com.niyazismayilov.githubrepostats.data.model.response.RepoListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {
    @GET("search/repositories")
    fun getRepoList(
        @Query("q") createdAt: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") page: Int
    ): Single<RepoListResponse?>
}