package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("hizbQuarter")
    var hizbQuarter: Int?,
    @SerializedName("juz")
    var juz: Int?,
    @SerializedName("manzil")
    var manzil: Int?,
    @SerializedName("page")
    var page: Int?,
    @SerializedName("ruku")
    var ruku: Int?,
    @SerializedName("sajda")
    var sajda: SajdaX?
)