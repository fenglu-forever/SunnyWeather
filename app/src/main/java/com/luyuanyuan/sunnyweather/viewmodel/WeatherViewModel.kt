package com.luyuanyuan.sunnyweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.luyuanyuan.sunnyweather.model.Repository
import com.luyuanyuan.sunnyweather.model.entity.Location

class WeatherViewModel : ViewModel() {
    private val loadWeatherData = MutableLiveData<Location>()

    var locationLat = ""

    var locationLng = ""

    var placeName = ""

    val mWeatherData = Transformations.switchMap(loadWeatherData) {
        Repository.loadWeather(it.lng, it.lat)
    }

    fun getWeather(lng: String, lat: String) {
        loadWeatherData.value = Location(lat, lng)
    }
}