package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class DataQuran(
    @SerializedName("name")
    var name: NamaSurah?,
    @SerializedName("number")
    var number: Int?,
    @SerializedName("numberOfVerses")
    var numberOfVerses: Int?,
    @SerializedName("preBismillah")
    var preBismillah: Any?,
    @SerializedName("revelation")
    var revelation: Revelation?,
    @SerializedName("sequence")
    var sequence: Int?,
    @SerializedName("tafsir")
    var tafsir: Tafsir?,
    @SerializedName("verses")
    var verses: List<Verse>?
)

data class NamaSurah(
    @SerializedName("long")
    var long: String?,
    @SerializedName("short")
    var short: String?,
    @SerializedName("translation")
    var translation: Translation?,
    @SerializedName("transliteration")
    var transliteration: Transliteration?
)

data class Verse(
    @SerializedName("audio")
    var audio: Audio?,
    @SerializedName("meta")
    var meta: Meta?,
    @SerializedName("number")
    var number: Number?,
    @SerializedName("tafsir")
    var tafsir: TafsirXX?,
    @SerializedName("text")
    var text: Text?,
    @SerializedName("translation")
    var translation: TranslationXX?
)
data class Audio(
    @SerializedName("primary")
    var primary: String?,
    @SerializedName("secondary")
    var secondary: List<String>?
)

data class Text(
    @SerializedName("arab")
    var arab: String?,
    @SerializedName("transliteration")
    var transliteration: TransliterationXX?
)

data class TransliterationXX(
    @SerializedName("en")
    var en: String?
)

data class TafsirXX(
    @SerializedName("id")
    var id: Id?
)
