package com.isaacsufyan.catchu.di

import com.isaacsufyan.catchu.domain.model.RawMeasurements
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.isaacsufyan.catchu.framework.datastore.preference.abstraction.PrefService
import com.isaacsufyan.catchu.framework.datastore.preference.implementation.PrefServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefModule {

    @Provides
    @Singleton
    fun providePrefService(prefServiceImpl: PrefServiceImpl): PrefService = prefServiceImpl

    @Provides
    @Singleton
    fun provideRawMeasurement(): RawMeasurements = RawMeasurements()

}