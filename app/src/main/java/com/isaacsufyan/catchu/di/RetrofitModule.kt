package com.isaacsufyan.catchu.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.isaacsufyan.catchu.BuildConfig
import com.isaacsufyan.catchu.framework.datastore.network.abstraction.ApiService
import com.isaacsufyan.catchu.framework.datastore.network.api.RestApis
import com.isaacsufyan.catchu.framework.datastore.network.implementation.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context,): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
//        return httpClient.build()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient().newBuilder().apply {
            // Add the base headers interceptor next
            if (BuildConfig.DEBUG) {
                addInterceptor(interceptor)
            }
        }
            .connectTimeout(45, TimeUnit.SECONDS).writeTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS).retryOnConnectionFailure(false)
            .retryOnConnectionFailure(false)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String, gson: Gson): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RestApis = retrofit.create(RestApis::class.java)

    @Provides
    @Singleton
    fun provideApiService(apiServiceImpl: ApiServiceImpl): ApiService = apiServiceImpl
}