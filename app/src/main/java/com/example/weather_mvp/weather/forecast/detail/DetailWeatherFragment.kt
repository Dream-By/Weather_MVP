package com.example.weather_mvp.weather.forecast.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.weather_mvp.R
import com.example.weather_mvp.network.WeatherApi
import com.example.weather_mvp.network.getIconUrl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_weather_fragment.*
import kotlinx.android.synthetic.main.detail_weather_fragment.imageView
import kotlinx.android.synthetic.main.detail_weather_fragment.textViewDescription
import kotlinx.android.synthetic.main.detail_weather_fragment.textViewTodayCity
import kotlinx.android.synthetic.main.detail_weather_fragment.textViewTodayTemp
import kotlinx.android.synthetic.main.detail_weather_fragment.textViewTodayTempMinMax
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            DetailWeatherFragment()
    }

    private lateinit var viewModel: DetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailWeatherViewModel::class.java)

        val weatherApi = WeatherApi()
        val position = arguments?.getInt("position")
        val city = arguments?.getString("city")
        val date = arguments?.getString("date")

        activity?.toolbar?.setTitle("Подробно погода: $city")

        GlobalScope.launch(Dispatchers.Main) {

            val weatherDetail = weatherApi.getForecast(city!!).await()

            textViewDetailDate.text = date
            textViewTodayCity.text = weatherDetail.city.name
            textViewTodayTemp.text = (weatherDetail.list[position!!].main.temp.toFloat()-273.15).toInt().toString() + "°C"
            textViewTodayTempMinMax.text = (weatherDetail.list[position].main.temp.toFloat()-273.15).toInt().toString() + "°C"+" / "+(weatherDetail.list[position].main.feelsLike.toFloat()-273.15).toInt().toString()+"°C"
            Glide.with(imageView.context).load(getIconUrl + weatherDetail.list[position].weather[0].icon + ".png").into(imageView)
            textViewDescription.text = weatherDetail.list[position].weather[0].description
        }


    }
}