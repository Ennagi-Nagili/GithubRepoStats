package com.niyazismayilov.githubrepostats.ui.activity

import com.niyazismayilov.githubrepostats.data.IDataManager
import com.niyazismayilov.githubrepostats.ui.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(var iDataManager: IDataManager?) : BaseViewModel()