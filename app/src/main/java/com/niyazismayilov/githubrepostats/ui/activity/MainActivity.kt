package com.niyazismayilov.githubrepostats.ui.activity

import android.os.Bundle
import com.niyazismayilov.githubrepostats.BR
import com.niyazismayilov.githubrepostats.R
import com.niyazismayilov.githubrepostats.databinding.ActivityMainBinding
import com.niyazismayilov.githubrepostats.di.component.ActivityComponent
import com.niyazismayilov.githubrepostats.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding?, MainActivityViewModel?>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }
}