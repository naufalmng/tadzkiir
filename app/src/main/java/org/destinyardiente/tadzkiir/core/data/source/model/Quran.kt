package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Quran(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: DataQuran?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)