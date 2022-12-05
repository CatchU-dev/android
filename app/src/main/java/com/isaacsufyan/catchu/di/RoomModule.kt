package com.isaacsufyan.catchu.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.isaacsufyan.catchu.framework.datastore.cache.abstraction.DaoService
import com.isaacsufyan.catchu.framework.datastore.cache.database.AppDatabase
import com.isaacsufyan.catchu.framework.datastore.cache.database.CacheDao
import com.isaacsufyan.catchu.framework.datastore.cache.implementation.CacheDaoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME: String = "MyDB"

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCacheDAO(blogDatabase: AppDatabase): CacheDao {
        return blogDatabase.cacheDao()
    }

    @Singleton
    @Provides
    fun provideDaoService(cacheDaoImpl: CacheDaoImpl): DaoService = cacheDaoImpl

}