package com.isaacsufyan.catchu.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ProviderModel(

    @SerializedName("provider_id")
    @Expose
    var providerId: Int? = null,

    @SerializedName("provider_name")
    @Expose
    var providerName: String? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null

) {
    override fun toString(): String {
        return name ?: ""
    }
}