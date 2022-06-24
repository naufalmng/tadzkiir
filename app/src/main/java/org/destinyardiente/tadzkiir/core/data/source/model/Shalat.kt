package org.destinyardiente.tadzkiir.core.data.source.model


import com.google.gson.annotations.SerializedName

data class Shalat(
    @SerializedName("date")
    var date: String?,
    @SerializedName("days")
    var days: Int?,
    @SerializedName("latitude")
    var latitude: Double?,
    @SerializedName("location")
    var location: String?,
    @SerializedName("longitude")
    var longitude: Double?,
    @SerializedName("times")
    var times: List<JadwalShalat>?,
    var address: String?,
//    var state: String?
)

data class JadwalShalat(
    @SerializedName("asr")
    var asr: String?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("fajr")
    var fajr: String?,
    @SerializedName("isha")
    var isha: String?,
    @SerializedName("maghrib")
    var maghrib: String?,
    @SerializedName("sunrise")
    var sunrise: String?,
    @SerializedName("zuhr")
    var zuhr: String?,
)
data class SavedJadwalShalat(
    var isObtained: Boolean?,
    var date: String?,
    var asr: String?,
    var fajr: String?,
    var isha: String?,
    var maghrib: String?,
    var sunrise: String?,
    var zuhr: String?,
    var location: String?,
)