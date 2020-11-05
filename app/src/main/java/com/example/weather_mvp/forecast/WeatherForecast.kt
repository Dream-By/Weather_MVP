package com.example.weather_mvp.forecast


import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<Item>,
    @SerializedName("message")
    val message: Int
)