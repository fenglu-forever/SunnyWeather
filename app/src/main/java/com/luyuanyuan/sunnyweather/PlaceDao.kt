package com.luyuanyuan.sunnyweather

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.luyuanyuan.sunnyweather.model.entity.Place
import com.luyuanyuan.sunnyweather.util.PREF_KEY_SAVE_PLACE
import com.luyuanyuan.sunnyweather.util.WEATHER_PREFERENCE

object PlaceDao {
    fun getSavePlace(): Place? {
        val pref = WeatherApp.getAppContext()
            .getSharedPreferences(WEATHER_PREFERENCE, Context.MODE_PRIVATE)
        val placeJsonStr = pref.getString(PREF_KEY_SAVE_PLACE, "")
        if (!TextUtils.isEmpty(placeJsonStr)) {
            return Gson().fromJson(placeJsonStr, Place::class.java)
        }
        return null
    }

    fun savePlace(place: Place) {
        val pref = WeatherApp.getAppContext()
            .getSharedPreferences(WEATHER_PREFERENCE, Context.MODE_PRIVATE)
        pref.edit().putString(PREF_KEY_SAVE_PLACE, Gson().toJson(place)).apply()
    }
}