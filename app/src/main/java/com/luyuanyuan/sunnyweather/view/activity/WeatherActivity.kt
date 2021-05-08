package com.luyuanyuan.sunnyweather.view.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luyuanyuan.sunnyweather.R
import com.luyuanyuan.sunnyweather.model.entity.Weather
import com.luyuanyuan.sunnyweather.model.entity.getSky
import com.luyuanyuan.sunnyweather.util.*
import com.luyuanyuan.sunnyweather.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.layout_daily.*
import kotlinx.android.synthetic.main.layout_life_index.*
import kotlinx.android.synthetic.main.layout_realtime.*

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.expandStatusBar()
        window.updateStatusBarColor(Color.TRANSPARENT)
        setContentView(R.layout.activity_weather)
        initViews()
        initListeners()
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.mWeatherData.observe(this, Observer {
            refreshWeatherLayout.isRefreshing = false
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                "无法获取天气信息".showToast()
                Log.d("fenglu", "eee", it.exceptionOrNull())
                it.exceptionOrNull()?.printStackTrace()
            }
        })
        refreshWeather()
    }

    private fun initViews() {
        refreshWeatherLayout.setColorSchemeResources(R.color.colorPrimary)
        placeFgContainer.setPadding(0, getStatusBarHeight(), 0, 0)
    }

    private fun initListeners() {
        refreshWeatherLayout.setOnRefreshListener {
            refreshWeather()
        }
        switchCity.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerClosed(drawerView: View) {
                val inputManger =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManger.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerOpened(drawerView: View) {
            }

        })
    }

    fun refreshWeather() {
        viewModel.getWeather(viewModel.locationLng, viewModel.locationLat)
        refreshWeatherLayout.isRefreshing = true
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.expandStatusBar()
        }
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realTime = weather.realtime
        val daily = weather.daily
        val realTimeSky = getSky(realTime.skycon)
        currentTemp.text = "${realTime.temperature.toFloat().toInt()}℃"
        currentSky.text = realTimeSky.info
        currentAqi.text = "空气指数${realTime.airQuality.aqi.chn.toInt()}"
        realtimeLayout.setBackgroundResource(realTimeSky.bg)
        dailyLayout.removeAllViews()
        val days = daily.skycon.size
        val layoutInflater = LayoutInflater.from(this)
        for (i in 0 until days) {
            val skyCon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = layoutInflater.inflate(R.layout.item_daily, dailyLayout, false)
            val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
            val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
            val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
            dateInfo.text = skyCon.date.split("T")[0]
            val sky = getSky(skyCon.value)
            skyInfo.text = sky.info
            skyIcon.setImageResource(sky.icon)
            temperatureInfo.text = "${temperature.min.toInt()} ~ ${temperature.max.toInt()}"
            dailyLayout.addView(view)
        }
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        crashWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }
}