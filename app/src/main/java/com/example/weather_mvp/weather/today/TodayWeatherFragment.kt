package com.example.weather_mvp.weather.today

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.weather_mvp.R
import com.example.weather_mvp.adapters.City_Name
import com.example.weather_mvp.network.WeatherApi
import com.example.weather_mvp.network.getIconUrl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.today_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TodayWeatherFragment : Fragment(),City_Name {

    companion object {
        fun newInstance() =
            TodayWeatherFragment()
    }

    private lateinit var viewModel: TodayWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.today_weather_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TodayWeatherViewModel::class.java)

        City_Name("Gomel")

        val weatherApi = WeatherApi()

        GlobalScope.launch (Dispatchers.Main) {

            val weatherDay = weatherApi.getToday("Gomel").await()


            //textViewToday.text = "Обновлено: " + LocalDateTime.now().hour+":"+LocalDateTime.now().minute
            textViewTodayCity.text = weatherDay.name
            textViewTodayTemp.text = (weatherDay.main.temp.toFloat()-273.15).toInt().toString() + "°C"
            textViewTodayTempMinMax.text = (weatherDay.main.temp.toFloat()-273.15).toInt().toString() + "°C"+" / "+(weatherDay.main.feelsLike.toFloat()-273.15).toInt().toString()+"°C"
            Glide.with(imageView.context).load(getIconUrl + weatherDay.weather[0].icon + ".png").into(imageView)
            textViewDescription.text = weatherDay.weather[0].description

        }
    }

    override fun City_Name(City: String) {
        activity?.toolbar?.setTitle("Текущий прогноз, $City")
    }

}