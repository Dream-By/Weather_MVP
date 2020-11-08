package com.example.weather_mvp.network

import com.example.weather_mvp.daily.WeatherDay
import com.example.weather_mvp.forecast.WeatherForecast
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Daily http://api.openweathermap.org/data/2.5/weather?q=Gomel&appid=4641d46a5986914b30ab23eadcc7b822
//Forecast http://api.openweathermap.org/data/2.5/forecast?q=Gomel&appid=4641d46a5986914b30ab23eadcc7b822

const val API_KEY = "4641d46a5986914b30ab23eadcc7b822"
const val getIconUrl = "http://openweathermap.org/img/w/"

interface WeatherApi {

    @GET("weather")
    fun getToday(@Query("q") location : String) : Deferred<WeatherDay>

    @GET("forecast")
    fun getForecast(@Query("q") location : String) : Deferred<WeatherForecast>

    companion object {
        operator fun invoke():WeatherApi {
            val requestInterceptor =Interceptor {chain ->
                val url =chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .build()
                val request =chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient =OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }


}