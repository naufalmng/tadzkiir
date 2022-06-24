package simpixie.com.mvpkotlin.model

import com.google.gson.annotations.SerializedName

data class TodayWeather(
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("temperature")
    val temperature: String
)