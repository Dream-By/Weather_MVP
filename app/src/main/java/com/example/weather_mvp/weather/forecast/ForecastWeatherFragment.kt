package com.example.weather_mvp.weather.forecast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather_mvp.R

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
    }

}