package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("en")
    var en: String?,
    @SerializedName("id")
    var id: String?
)