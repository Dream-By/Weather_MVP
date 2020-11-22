package com.example.weather_mvp.weather.today

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.weather_mvp.R
import com.example.weather_mvp.network.WeatherApi
import com.example.weather_mvp.network.getIconUrl
import com.example.weather_mvp.utils.Utils
import com.example.weather_mvp.utils.Utils.Companion.cityPrefGet
import com.example.weather_mvp.utils.Utils.Companion.isInternetAvailable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.today_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

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

        val networkResult = context?.let { isInternetAvailable(it) }

        if (networkResult !=false) {

            val city = cityPrefGet(activity as AppCompatActivity)
            activity?.toolbar?.setTitle("Текущий прогноз, $city")

            val weatherApi = WeatherApi()

            GlobalScope.launch (Dispatchers.Main) {
                try {
                    val weatherDay = city?.let { weatherApi.getToday(it).await() }

                    textViewTodayCity.text = weatherDay.name
                    textViewTodayTemp.text = (weatherDay!!.main.temp.toFloat()-273.15).toInt().toString() + "°C"
                    textViewTodayTempMinMax.text = (weatherDay.main.temp.toFloat()-273.15).toInt().toString() + "°C"+" / "+(weatherDay.main.feelsLike.toFloat()-273.15).toInt().toString()+"°C"
                    Glide.with(imageView.context).load(getIconUrl + weatherDay.weather[0].icon + ".png").into(imageView)
                    textViewDescription.text = weatherDay.weather[0].description

                } catch (e:Exception) {
                    Toast.makeText(activity, "Уточните название населенного пункта", Toast.LENGTH_LONG).show()
                    Utils.cityPrefPut(activity as AppCompatActivity, "Gomel")
                    view?.findNavController()?.navigate(R.id.todayWeatherFragment)
                }

            }


        } else {
            activity?.toolbar?.setTitle("Текущий прогноз, ")
            Toast.makeText(activity, "Нет подключения к сети", Toast.LENGTH_LONG).show()

        }

            }

}