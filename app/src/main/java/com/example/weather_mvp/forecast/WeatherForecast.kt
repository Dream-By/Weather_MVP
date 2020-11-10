package com.example.weather_mvp.forecast


import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class WeatherForecast(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<com.example.weather_mvp.forecast.List>,
    @SerializedName("message")
    val message: Int
)