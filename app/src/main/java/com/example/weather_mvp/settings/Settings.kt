package com.example.weather_mvp.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.weather_mvp.R
import com.example.weather_mvp.utils.Utils.Companion.cityPrefPut
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.settings_fragment.*

class Settings : Fragment() {

    companion object {
        fun newInstance() = Settings()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.settings_fragment, container, false)

        val buttonMoscow = view.findViewById<Button>(R.id.buttonMoscow)
        val buttonSaintPetersburg = view.findViewById<Button>(R.id.buttonSaintPetersburg)
        val buttonCityOK = view.findViewById<Button>(R.id.buttonCityOK)

        buttonMoscow.setOnClickListener{view->
            //Toast.makeText(activity, "Moscow", Toast.LENGTH_LONG).show()
                cityPrefPut (activity as AppCompatActivity, "Moscow")
                view.findNavController().navigate(R.id.todayWeatherFragment)
        }


        buttonSaintPetersburg.setOnClickListener { view->
            //Toast.makeText(activity, "Saint Petersburg", Toast.LENGTH_LONG).show()
            cityPrefPut (activity as AppCompatActivity, "Saint Petersburg")
            view.findNavController().navigate(R.id.todayWeatherFragment)
        }

        buttonCityOK.setOnClickListener{view->
            if (editTextCityName.text.toString() =="City" || editTextCityName.text.toString() =="") {
                Toast.makeText(activity, "Введите свой город", Toast.LENGTH_LONG).show()
            }
            else {
                cityPrefPut (activity as AppCompatActivity, editTextCityName.text.toString())
                view.findNavController().navigate(R.id.todayWeatherFragment)

            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        activity?.toolbar?.setTitle("Выбор города:")
    }

}