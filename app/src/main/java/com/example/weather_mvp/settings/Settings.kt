package com.example.weather_mvp.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.weather_mvp.R
import kotlinx.android.synthetic.main.activity_main.*

class Settings : Fragment() {

    companion object {
        fun newInstance() = Settings()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navController = findNavController()

        val view = inflater.inflate(R.layout.settings_fragment, container, false)

        val buttonMoscow = view.findViewById<Button>(R.id.buttonMoscow)
        val buttonSaintPetersburg = view.findViewById<Button>(R.id.buttonSaintPetersburg)

        buttonMoscow.setOnClickListener{view->
            Toast.makeText(activity, "Moscow", Toast.LENGTH_LONG).show()
            try {
                //организуем sharedpreferences
                var cityPref : SharedPreferences? = activity?.getSharedPreferences("city",Context.MODE_PRIVATE)
                val editor = cityPref?.edit()
                editor?.putString("city","Moscow")?.apply()
                //
 //               val bundle = Bundle()
 //               val city = "Moscow"
 //               bundle.putString("city",city)
                //view.findNavController().navigate(R.id.todayWeatherFragment,bundle)
                view.findNavController().navigate(R.id.todayWeatherFragment)
            }catch (ignored : ClassCastException) {}
        }


        buttonSaintPetersburg.setOnClickListener { view->
            Toast.makeText(activity, "Saint Petersburg", Toast.LENGTH_LONG).show()
            try {
                //val bundle = Bundle()
                //val city = "Saint Petersburg"
                //bundle.putString("city",city)
                var cityPref : SharedPreferences? = activity?.getSharedPreferences("city",Context.MODE_PRIVATE)
                val editor = cityPref?.edit()
                editor?.putString("city","Saint Petersburg")?.apply()
                view.findNavController().navigate(R.id.todayWeatherFragment)
            }catch (ignored : ClassCastException) {}
        }


        //return inflater.inflate(R.layout.settings_fragment, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        activity?.toolbar?.setTitle("Выбор города:")
    }

}