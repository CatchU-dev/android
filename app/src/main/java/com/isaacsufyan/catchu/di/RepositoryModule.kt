package com.isaacsufyan.catchu.di

import com.isaacsufyan.catchu.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.isaacsufyan.catchu.framework.datastore.cache.abstraction.DaoService
import com.isaacsufyan.catchu.framework.datastore.network.abstraction.ApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        cacheService: DaoService,
    ): Repository {
        return Repository(
            apiService,
            cacheService
        )
    }
}