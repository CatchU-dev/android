package com.isaacsufyan.catchu.framework.datastore.cache.abstraction

import com.isaacsufyan.catchu.domain.model.ResponseModel


interface DaoService {
    suspend fun getMovieList(sampleModel: ResponseModel)
}