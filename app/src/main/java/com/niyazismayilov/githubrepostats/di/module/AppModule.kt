package com.niyazismayilov.githubrepostats.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.niyazismayilov.githubrepostats.data.AppDataManager
import com.niyazismayilov.githubrepostats.data.IDataManager
import com.niyazismayilov.githubrepostats.data.api.IApi
import com.niyazismayilov.githubrepostats.data.local.CachedRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideApi(retrofit: Retrofit): IApi {
        return retrofit.create<IApi>(IApi::class.java)
    }

    @Provides
    fun provideDataManager(iApiMethod: IApi?, iCacheRepository: CachedRepository?): IDataManager {
        return AppDataManager(iApiMethod!!, iCacheRepository!!)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson?, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory?,
        okHttpClient: OkHttpClient?
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(rxJava2CallAdapterFactory!!)
            .addConverterFactory(GsonConverterFactory.create(gson!!))
            .client(okHttpClient!!)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRxCallAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}