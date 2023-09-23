package com.niyazismayilov.githubrepostats

import android.app.Application
import com.niyazismayilov.githubrepostats.di.component.AppComponent
import com.niyazismayilov.githubrepostats.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ApplicationClass : Application(), HasAndroidInjector {
    var appComponent: AppComponent? = null

    var androidInjector: DispatchingAndroidInjector<Any>? = null
        @Inject set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)!!
            .build()
        appComponent!!.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector!!
    }
}