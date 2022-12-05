package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BilleeModel(

    @SerializedName("billee_id")
    @Expose
    var billeeId: Int? = null,

    @SerializedName("billee_name")
    @Expose
    var billeeName: String? = null,

    @SerializedName("id")
    @Expose
    var Id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null
){
    override fun toString(): String {
        return billeeName ?: name ?: ""
    }
}