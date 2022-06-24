package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class DataKota(
    @SerializedName("id")
    var id: String?,
    @SerializedName("nama")
    var nama: String?
)