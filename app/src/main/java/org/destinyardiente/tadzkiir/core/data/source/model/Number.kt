package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Number(
    @SerializedName("inQuran")
    var inQuran: Int?,
    @SerializedName("inSurah")
    var inSurah: Int?
)