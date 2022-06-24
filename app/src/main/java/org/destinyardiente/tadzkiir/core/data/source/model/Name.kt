package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("long")
    var long: String?,
    @SerializedName("short")
    var short: String?,
    @SerializedName("translation")
    var translation: Translation?,
    @SerializedName("transliteration")
    var transliteration: Transliteration?
)