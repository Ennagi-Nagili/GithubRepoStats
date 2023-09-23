package com.niyazismayilov.githubrepostats.di.module

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.niyazismayilov.githubrepostats.data.IDataManager
import com.niyazismayilov.githubrepostats.ui.activity.MainActivityViewModel
import com.niyazismayilov.githubrepostats.ui.base.BaseActivity
import com.niyazismayilov.githubrepostats.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity<*, *>) {
    @Provides
    fun provideFeedViewModel(dataManager: IDataManager?): MainActivityViewModel {
        val supplier = Supplier { MainActivityViewModel(dataManager) }
        val factory = ViewModelProviderFactory(
            MainActivityViewModel::class.java, supplier
        )
        return ViewModelProvider(activity, factory as ViewModelProvider.Factory)[MainActivityViewModel::class.java]
    }
}