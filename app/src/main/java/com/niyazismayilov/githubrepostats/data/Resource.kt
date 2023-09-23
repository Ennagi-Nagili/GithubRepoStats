package com.niyazismayilov.githubrepostats.data

class Resource<T> private constructor(val type: Int, d: T?, e: Throwable?) {
    val data: T
    val error: Throwable?

    init {
        data = d!!
        error = e
    }

    companion object {
        const val INITIAL = 0
        const val LOADING = 1
        const val SUCCESS = 2
        const val ERROR = 3
        fun <T> initial(): Resource<T?> {
            return Resource(INITIAL, null, null)
        }

        fun <T> loading(): Resource<T?> {
            return Resource(LOADING, null, null)
        }

        fun <T> success(d: T): Resource<T> {
            return Resource(SUCCESS, d, null)
        }

        fun <T> error(e: Throwable?): Resource<T> {
            return Resource(ERROR, null, e)
        }
    }
}