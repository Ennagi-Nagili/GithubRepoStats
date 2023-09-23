package com.niyazismayilov.githubrepostats.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    var compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}