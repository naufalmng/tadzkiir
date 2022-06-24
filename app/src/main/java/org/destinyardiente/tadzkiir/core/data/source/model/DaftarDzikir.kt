package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class DaftarDzikir(
    @SerializedName("data")
    var `data`: ArrayList<DataDaftarDzikir>?
)