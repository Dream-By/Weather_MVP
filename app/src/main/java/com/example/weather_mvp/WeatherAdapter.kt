package com.example.weather_mvp

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather_mvp.WeatherAdapter.ViewHolder
import com.example.weather_mvp.forecast.List
import com.example.weather_mvp.forecast.Weather
import com.example.weather_mvp.network.getIconUrl
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.LocalDateTime

class WeatherAdapter(context: Context,val weatherForecast : ArrayList<List>) : RecyclerView.Adapter<ViewHolder>() {

    private val context = context

    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){

            private val temperature_text : TextView = itemView.findViewById(R.id.textViewTemperature)
            private val weatherdescription_text : TextView = itemView.findViewById(R.id.textViewDescription)
            private val weatherIcon : ImageView = itemView.findViewById(R.id.imageViewIconWeather)
            private val dateForDays : TextView = itemView.findViewById(R.id.textViewDate)

        fun bind (temp : String, description : String, iconulr : String, date : String) {
            temperature_text.text = temp
            weatherdescription_text.text = description
            Glide.with(itemView.context).load(getIconUrl + iconulr + ".png").into(weatherIcon)
            dateForDays.text = date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.forecast_weather,parent,false)
            return ViewHolder(v)
    }

    override fun getItemCount(): Int {
          return 7 //прогноз на 7 дней
        //return weatherForecast.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val weatherArray : Item = weatherForecast[position]

        val temp = (weatherForecast[position].main.temp.toFloat()-273.15).toInt().toString()+"°C"
        val description = weatherForecast[position].weather[0].description
        val iconulr = weatherForecast[position].weather[0].icon
        val date = LocalDateTime.now().plusDays(position.toLong()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        holder.bind(temp,description,iconulr,date)

        
        }
}