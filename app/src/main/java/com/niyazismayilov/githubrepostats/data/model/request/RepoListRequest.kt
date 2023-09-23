package com.niyazismayilov.githubrepostats.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepoListRequest(
    @field:SerializedName("q") @field:Expose var createdAt: String?,
    @field:SerializedName(
        "sort"
    ) @field:Expose val sort: String,
    @field:SerializedName("order") @field:Expose val order: String,
    @field:SerializedName(
        "page"
    ) @field:Expose var page: Int
) {

    fun goNextPage() {
        page++
    }
}