package com.isaacsufyan.catchu.framework.datastore.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.framework.datastore.cache.TypeConverterToJson

@Database(entities = [ResponseModel::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverterToJson::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}