package com.example.weather_mvp

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.navigation.*
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weather_mvp.weather.today.TodayWeatherFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle as Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private var checkNetwork : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val cityPrefGet : SharedPreferences? = getSharedPreferences("city", Context.MODE_PRIVATE)
        var city = cityPrefGet?.getString("city","")

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController)

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {

                    if (checkNetwork !=true) {
                        checkNetwork = true
                        //Toast.makeText(this@MainActivity, "Появилось подключение к сети", Toast.LENGTH_LONG).show()
                        val fragmentManager = supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        val fragment = TodayWeatherFragment()
                        fragmentTransaction.add(R.id.nav_host_fragment,fragment)
                        fragmentTransaction.commit()

                    }
                }
                override fun onLost(network: Network) {
                    checkNetwork = false
                    Toast.makeText(this@MainActivity, "Нет подключения к сети", Toast.LENGTH_LONG).show()
                }
            })

        }
    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp()
    }


}