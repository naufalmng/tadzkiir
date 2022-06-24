package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Juz(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: DataJuz?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)

data class DataJuz(
    @SerializedName("juz")
    var juz: Int?,
    @SerializedName("juzEndInfo")
    var juzEndInfo: String?,
    @SerializedName("juzEndSurahNumber")
    var juzEndSurahNumber: Int?,
    @SerializedName("juzStartInfo")
    var juzStartInfo: String?,
    @SerializedName("juzStartSurahNumber")
    var juzStartSurahNumber: Int?,
    @SerializedName("totalVerses")
    var totalVerses: Int?,
    @SerializedName("verses")
    var verses: List<Verse>?
)