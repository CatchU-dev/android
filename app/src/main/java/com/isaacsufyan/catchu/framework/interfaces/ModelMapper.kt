package com.isaacsufyan.catchu.framework.interfaces

interface ModelMapper <DataModel, DomainModel>{

    fun mapToDomainModel(dataModel: DataModel): DomainModel

    fun mapToDataModel(domainModel: DomainModel): DataModel
}