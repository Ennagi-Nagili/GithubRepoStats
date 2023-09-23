package com.niyazismayilov.githubrepostats.di.module

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.niyazismayilov.githubrepostats.data.IDataManager
import com.niyazismayilov.githubrepostats.ui.base.BaseFragment
import com.niyazismayilov.githubrepostats.ui.fragment.favlist.FavoriteListViewModel
import com.niyazismayilov.githubrepostats.ui.fragment.repolist.RepoListViewModel
import com.niyazismayilov.githubrepostats.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class FragmentBuilderModule(private val fragment: BaseFragment<*, *>) {
    @Provides
    fun provideAboutViewModel(dataManager: IDataManager?): RepoListViewModel {
        val supplier = Supplier { RepoListViewModel(dataManager!!) }
        val factory: ViewModelProviderFactory<*> = ViewModelProviderFactory(
            RepoListViewModel::class.java, supplier
        )
        return ViewModelProvider(fragment, factory as ViewModelProvider.Factory).get<RepoListViewModel>(
            RepoListViewModel::class.java
        )
    }

    @Provides
    fun provideFavoriteViewModel(dataManager: IDataManager?): FavoriteListViewModel {
        val supplier = Supplier { FavoriteListViewModel(dataManager!!) }
        val factory: ViewModelProviderFactory<*> = ViewModelProviderFactory(
            FavoriteListViewModel::class.java, supplier
        )
        return ViewModelProvider(fragment, factory as ViewModelProvider.Factory)[FavoriteListViewModel::class.java]
    }
}