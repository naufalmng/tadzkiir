package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Revelation(
    @SerializedName("arab")
    var arab: String?,
    @SerializedName("en")
    var en: String?,
    @SerializedName("id")
    var id: String?
)