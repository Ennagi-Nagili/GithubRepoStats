package com.niyazismayilov.githubrepostats.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepoOwner(
    @field:SerializedName("avatar_url") @field:Expose val avatar_url: String?,
    @field:SerializedName(
        "login"
    ) @field:Expose val login: String?,
    @field:SerializedName("html_url") @field:Expose val html_url: String?
)