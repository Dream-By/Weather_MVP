package com.example.weather_mvp.weather.forecast

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_mvp.R
import com.example.weather_mvp.adapters.City_Name
import com.example.weather_mvp.adapters.List_Position
import com.example.weather_mvp.adapters.RecyclerItemClickListener
import com.example.weather_mvp.adapters.WeatherAdapter
import com.example.weather_mvp.forecast.List
import com.example.weather_mvp.network.WeatherApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.forecast_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class ForecastWeatherFragment : Fragment(),City_Name {

    companion object {
        fun newInstance() =
            ForecastWeatherFragment()
    }

    private lateinit var viewModel: ForecastWeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForecastWeatherViewModel::class.java)

        var cityPrefGet : SharedPreferences? = activity?.getSharedPreferences("city", Context.MODE_PRIVATE)
        var city = cityPrefGet?.getString("city","").toString()
        if (city == ""){
            city = "Gomel"
        }

        City_Name(city)

        val weatherApi = WeatherApi()

        GlobalScope.launch(Dispatchers.Main) {

            val weatherForecast = weatherApi.getForecast(city).await()

            val adapter = context?.let {
                WeatherAdapter(
                    it,
                    weatherForecast.list as ArrayList<List>
                )
            }

            val rw : RecyclerView? = view?.findViewById(R.id.rw)
            if (rw != null) {
                rw.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
                rw.adapter =adapter
                //кодим для item
                rw.addOnItemTouchListener(
                    RecyclerItemClickListener(
                        activity!!,
                        rw,
                        object :
                            RecyclerItemClickListener.OnItemClickListener {
                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun onItemClick(view: View, position: Int) {
                                Toast.makeText(activity, "position $position", Toast.LENGTH_SHORT)
                                    .show()
                                //переход на detailfragment
                                try {
                                    val bundle = Bundle()
                                    bundle.putInt("position",position)
                                    //
                                    //val city = "Gomel"
                                    //
                                    bundle.putString("city",city)
                                    val date = LocalDateTime.now().plusDays(position.toLong()).format(
                                        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
                                    bundle.putString("date",date)
                                    view.findNavController().navigate(R.id.detailWeatherFragment,bundle)
                                    }
                                catch (ignored : ClassCastException) {}

                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                Toast.makeText(
                                    activity,
                                    "It's working onItemLongClick",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                )
            }
        }
    }

    override fun City_Name(City: String) {
        activity?.toolbar?.setTitle("Прогноз погоды на 7 дней, $City")
    }
}