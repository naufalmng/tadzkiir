package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class DataDaftarDzikir(
    @SerializedName("description_doa")
    var descriptionDoa: List<String>?,
    @SerializedName("doa_arab")
    var doaArab: List<String>?,
    @SerializedName("title")
    var title: String?
)