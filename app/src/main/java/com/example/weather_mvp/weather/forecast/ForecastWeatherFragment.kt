package com.example.weather_mvp.weather.forecast

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_mvp.R
import com.example.weather_mvp.RecyclerItemClickListener
import com.example.weather_mvp.WeatherAdapter
import com.example.weather_mvp.forecast.List
import com.example.weather_mvp.network.WeatherApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForecastWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            ForecastWeatherFragment()
    }

    private lateinit var viewModel: ForecastWeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        activity?.toolbar?.setTitle("Прогноз погоды на 7 дней")

        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForecastWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        val weatherApi = WeatherApi()

        GlobalScope.launch(Dispatchers.Main) {

            val weatherForecast = weatherApi.getForecast("Gomel").await()

            val adapter = context?.let { WeatherAdapter(it, weatherForecast.list as ArrayList<List>) }

            val rw : RecyclerView? = view?.findViewById(R.id.rw)
            if (rw != null) {
                rw.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
                rw.adapter =adapter
                //кодим для item
                rw.addOnItemTouchListener(RecyclerItemClickListener(activity!!,rw,object:RecyclerItemClickListener.OnItemClickListener{
                    override fun onItemClick(view: View, position: Int) {
                        Toast.makeText(activity, "position $position", Toast.LENGTH_SHORT).show()
                        //переход на detailfragment

                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        Toast.makeText(activity, "It's working onItemLongClick", Toast.LENGTH_SHORT).show()
                    }

                }))
            }
        }
    }
}