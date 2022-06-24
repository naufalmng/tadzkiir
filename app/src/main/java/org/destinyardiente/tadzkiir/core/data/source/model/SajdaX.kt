package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class SajdaX(
    @SerializedName("obligatory")
    var obligatory: Boolean?,
    @SerializedName("recommended")
    var recommended: Boolean?
)