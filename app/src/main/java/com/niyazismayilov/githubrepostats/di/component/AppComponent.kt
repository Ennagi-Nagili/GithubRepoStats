package com.niyazismayilov.githubrepostats.di.component

import android.app.Application
import androidx.annotation.Nullable
import com.niyazismayilov.githubrepostats.ApplicationClass
import com.niyazismayilov.githubrepostats.data.IDataManager
import com.niyazismayilov.githubrepostats.di.module.AppModule
import com.niyazismayilov.githubrepostats.di.module.RoomModule
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class, RoomModule::class])
interface AppComponent {
    fun inject(app: ApplicationClass?)
    val dataManager: IDataManager?

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }
}