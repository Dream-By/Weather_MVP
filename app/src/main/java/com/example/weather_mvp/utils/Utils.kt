package com.example.weather_mvp.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class Utils {

    companion object {

        fun cityPrefPut (activity: AppCompatActivity, city: String) {
            var cityPref : SharedPreferences? = activity?.getSharedPreferences("city", Context.MODE_PRIVATE)
            val editor = cityPref?.edit()
            editor?.putString("city",city)?.apply()
        }

        fun cityPrefGet(activity: AppCompatActivity) : String {

            var cityPrefGet : SharedPreferences? = activity?.getSharedPreferences("city", Context.MODE_PRIVATE)
            var city = cityPrefGet?.getString("city","").toString()

            if (city == ""){
                city = "Gomel"
            }
            return city
        }
    }
}