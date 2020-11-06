package com.example.weather_mvp.weather.forecast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather_mvp.R
import com.example.weather_mvp.network.WeatherApi
import kotlinx.android.synthetic.main.forecast_weather_fragment.*
import kotlinx.android.synthetic.main.today_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForecastWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            ForecastWeatherFragment()
    }

    private lateinit var viewModel: ForecastWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForecastWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        val weatherApi = WeatherApi()

        GlobalScope.launch(Dispatchers.Main) {
            val weatherForecast = weatherApi.getForecast("Gomel").await()
            textViewForecast.text = weatherForecast.list[0].main.toString() + weatherForecast.list[0].weather.toString()
        }
    }
}