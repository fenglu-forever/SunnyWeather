package com.luyuanyuan.sunnyweather.model.http

import androidx.lifecycle.liveData
import com.luyuanyuan.sunnyweather.R
import com.luyuanyuan.sunnyweather.WeatherApp
import com.luyuanyuan.sunnyweather.model.entity.Weather
import com.luyuanyuan.sunnyweather.util.OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object WeatherNetwork {
    fun loadPlaceList(query: String) = fire(Dispatchers.IO) {
        val response = ServiceCreator.create<PlaceService>().getPlaceList(query).requestNetwork()
        if (OK == response.status) Result.success(response.places)
        else Result.failure(IOException(WeatherApp.getAppContext().getString(R.string.server_response_failed)))
    }

    fun loadWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val weatherService = ServiceCreator.create<WeatherService>()
            val realtimeDeferred = async {
                weatherService.getRealtimeWeather(lng, lat).requestNetwork()
            }

            val dailyDeferred = async {
                weatherService.getDailyWeather(lng, lat).requestNetwork()
            }
            val realtimeResponse = realtimeDeferred.await()
            val dailyResponse = dailyDeferred.await()
            if (OK == realtimeResponse.status && OK == dailyResponse.status) {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(IOException(WeatherApp.getAppContext().getString(R.string.server_response_failed)))
            }
        }
    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}