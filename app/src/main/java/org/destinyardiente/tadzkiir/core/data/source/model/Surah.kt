package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Surah(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: List<DataSurah>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)

data class DataSurah(
    @SerializedName("name")
    var name: Name?,
    @SerializedName("number")
    var number: Int?,
    @SerializedName("numberOfVerses")
    var numberOfVerses: Int?,
    @SerializedName("revelation")
    var revelation: Revelation?,
    @SerializedName("sequence")
    var sequence: Int?,
    @SerializedName("tafsir")
    var tafsir: Tafsir?
): Serializable