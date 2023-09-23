package com.niyazismayilov.githubrepostats.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.niyazismayilov.githubrepostats.ApplicationClass
import com.niyazismayilov.githubrepostats.di.component.ActivityComponent
import com.niyazismayilov.githubrepostats.di.component.DaggerActivityComponent
//import com.niyazismayilov.githubrepostats.di.component.DaggerActivityComponent
import com.niyazismayilov.githubrepostats.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding?, V : BaseViewModel?> : AppCompatActivity(),
    BaseFragment.Callback {
    var viewDataBinding: T? = null
    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    var mViewModel: V? = null
        @Inject set
    protected override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(buildComponent)
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    abstract fun performDependencyInjection(buildComponent: ActivityComponent)
    private val buildComponent: ActivityComponent
        private get() = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .appComponent((application as ApplicationClass).appComponent)
            .build()

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView<T>(this, layoutId)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String?) {}
}