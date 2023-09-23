package com.niyazismayilov.githubrepostats.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.niyazismayilov.githubrepostats.ApplicationClass
import com.niyazismayilov.githubrepostats.di.component.DaggerFragmentComponent
import com.niyazismayilov.githubrepostats.di.component.FragmentComponent
import com.niyazismayilov.githubrepostats.di.module.FragmentBuilderModule
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding?, V : BaseViewModel?> : Fragment() {
    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set

    var mViewModel: V? = null
        @Inject set
    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>
            baseActivity = activity
            activity.onFragmentAttached()
        }
    }

    abstract fun performDependencyInjection(buildComponent: FragmentComponent)
    private val buildComponent: FragmentComponent
        get() = DaggerFragmentComponent.builder()
            .appComponent((requireContext().applicationContext as ApplicationClass).appComponent)
            .fragmentBuilderModule(FragmentBuilderModule(this))
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(buildComponent)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }
}