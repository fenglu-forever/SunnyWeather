package com.luyuanyuan.sunnyweather.model

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.luyuanyuan.sunnyweather.PlaceDao
import com.luyuanyuan.sunnyweather.WeatherApp
import com.luyuanyuan.sunnyweather.model.entity.Place
import com.luyuanyuan.sunnyweather.model.http.*
import com.luyuanyuan.sunnyweather.util.PREF_KEY_SAVE_PLACE
import com.luyuanyuan.sunnyweather.util.WEATHER_PREFERENCE

object Repository {

    fun loadPlaceList(query: String) = WeatherNetwork.loadPlaceList(query)

    fun loadWeather(lng: String, lat: String) = WeatherNetwork.loadWeather(lng, lat)

    fun getSavePlace() = PlaceDao.getSavePlace()

    fun savePlace(place: Place) = PlaceDao.savePlace(place)
}