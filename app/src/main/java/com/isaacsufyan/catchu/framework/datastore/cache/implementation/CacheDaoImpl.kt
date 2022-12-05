package com.isaacsufyan.catchu.framework.datastore.cache.implementation

import com.isaacsufyan.catchu.domain.model.ResponseModel
import com.isaacsufyan.catchu.framework.datastore.cache.abstraction.DaoService
import com.isaacsufyan.catchu.framework.datastore.cache.database.CacheDao
import javax.inject.Inject

class CacheDaoImpl @Inject constructor(private val cacheDao: CacheDao) : DaoService {
    override suspend fun getMovieList(sampleModel: ResponseModel) {
        return cacheDao.getMovieLst(sampleModel)
    }
}