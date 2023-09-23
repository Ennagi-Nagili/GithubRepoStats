package com.niyazismayilov.githubrepostats.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepoItem(
    var id: Int,
    @field:SerializedName("description") @field:Expose val description: String?,
    @field:SerializedName(
        "name"
    ) @field:Expose val name: String?,
    @field:SerializedName("stargazers_count") @field:Expose val stars: String?,
    @field:SerializedName(
        "language"
    ) @field:Expose val language: String?,
    @field:SerializedName("forks") @field:Expose val forks: String?,
    @field:SerializedName(
        "created_at"
    ) @field:Expose val created_at: String?,
    @field:SerializedName("owner") @field:Expose val repoOwner: RepoOwner
)