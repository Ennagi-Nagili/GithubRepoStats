package com.niyazismayilov.githubrepostats.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.niyazismayilov.githubrepostats.utils.Constants

@Entity(tableName = Constants.TABLE_FAVS)
class CachedModel(
    val description: String?,
    val name: String?,
    val stargazers_count: String?,
    val avatar_url: String?,
    val language: String?,
    val forks: String?,
    val created_at: String?,
    val html_url: String?,
    val login: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}