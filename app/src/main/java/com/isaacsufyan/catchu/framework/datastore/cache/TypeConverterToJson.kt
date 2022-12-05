package com.isaacsufyan.catchu.framework.datastore.cache

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.isaacsufyan.catchu.domain.model.ActiveGroupTest
import com.isaacsufyan.catchu.domain.model.BilleeModel
import com.isaacsufyan.catchu.domain.model.ProviderModel
import com.isaacsufyan.catchu.domain.model.TestHistoryModel

class TypeConverterToJson {

    @TypeConverter
    fun listActiveGroupTestToJson(value: List<ActiveGroupTest>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToActiveGroupTestList(value: String) =
        Gson().fromJson(value, Array<ActiveGroupTest>::class.java).toList()

    @TypeConverter
    fun listProviderToJson(value: List<ProviderModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToProviderList(value: String) =
        Gson().fromJson(value, Array<ProviderModel>::class.java).toList()

    @TypeConverter
    fun listBilleeToJson(value: List<BilleeModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToBilleeList(value: String) =
        Gson().fromJson(value, Array<BilleeModel>::class.java).toList()

    @TypeConverter
    fun listHistoryListToJson(value: List<TestHistoryModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToTestHistoryList(value: String) =
        Gson().fromJson(value, Array<TestHistoryModel>::class.java).toList()

}