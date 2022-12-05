package com.isaacsufyan.catchu.framework.datastore.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.isaacsufyan.catchu.domain.model.ResponseModel

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun getMovieLst(sampleModel: ResponseModel)
}