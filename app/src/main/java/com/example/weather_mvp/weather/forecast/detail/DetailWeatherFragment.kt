package com.example.weather_mvp.weather.forecast.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather_mvp.R
import com.example.weather_mvp.adapters.City_Name
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_weather_fragment.*

class DetailWeatherFragment : Fragment(),City_Name {

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
        // TODO: Use the ViewModel
        City_Name("Gomel")
    }

    override fun City_Name(City: String) {
        textViewDetail.text = "$City"
        activity?.toolbar?.setTitle("Подробно погода: $City")
    }

}