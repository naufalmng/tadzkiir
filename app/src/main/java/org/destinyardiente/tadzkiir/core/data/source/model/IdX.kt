package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class IdX(
    @SerializedName("long")
    var long: String?,
    @SerializedName("short")
    var short: String?
)