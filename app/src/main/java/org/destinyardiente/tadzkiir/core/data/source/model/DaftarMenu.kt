package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class DaftarMenu(
    @SerializedName("data")
    var `data`: List<DataDaftarMenu>?
)