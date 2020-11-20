package com.example.weather_mvp.weather.today

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavArgument
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weather_mvp.R
import com.example.weather_mvp.adapters.City_Name
import com.example.weather_mvp.network.WeatherApi
import com.example.weather_mvp.network.getIconUrl
import com.example.weather_mvp.utils.Utils.Companion.cityPrefGet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.today_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TodayWeatherFragment : Fragment() {

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
        //делаем sharedpreferences

        val city = cityPrefGet(activity as AppCompatActivity)
        activity?.toolbar?.setTitle("Текущий прогноз, $city")

        val weatherApi = WeatherApi()

        GlobalScope.launch (Dispatchers.Main) {

            val weatherDay = city?.let { weatherApi.getToday(it).await() }


            //textViewToday.text = "Обновлено: " + LocalDateTime.now().hour+":"+LocalDateTime.now().minute
            if (weatherDay != null) {
                textViewTodayCity.text = weatherDay.name
            }
            textViewTodayTemp.text = (weatherDay!!.main.temp.toFloat()-273.15).toInt().toString() + "°C"
            textViewTodayTempMinMax.text = (weatherDay.main.temp.toFloat()-273.15).toInt().toString() + "°C"+" / "+(weatherDay.main.feelsLike.toFloat()-273.15).toInt().toString()+"°C"
            Glide.with(imageView.context).load(getIconUrl + weatherDay.weather[0].icon + ".png").into(imageView)
            textViewDescription.text = weatherDay.weather[0].description

        }
    }

}