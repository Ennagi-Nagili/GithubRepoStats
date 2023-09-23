package com.niyazismayilov.githubrepostats.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepoListResponse {
    @Expose
    @SerializedName("total_count")
    val totalCount = 0

    @Expose
    @SerializedName("items")
    val repoItemList: List<RepoItem>? = null
}