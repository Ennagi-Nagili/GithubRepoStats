package com.niyazismayilov.githubrepostats.di.component

import com.niyazismayilov.githubrepostats.di.module.ActivityModule
import com.niyazismayilov.githubrepostats.di.scope.ActivityScope
import com.niyazismayilov.githubrepostats.ui.activity.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity?)
}