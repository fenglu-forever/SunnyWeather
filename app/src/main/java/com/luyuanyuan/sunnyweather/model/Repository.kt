package com.luyuanyuan.sunnyweather.model

import androidx.lifecycle.liveData
import com.luyuanyuan.sunnyweather.R
import com.luyuanyuan.sunnyweather.WeatherApp
import com.luyuanyuan.sunnyweather.model.entity.Place
import com.luyuanyuan.sunnyweather.model.http.PlaceService
import com.luyuanyuan.sunnyweather.model.http.ServiceCreator
import com.luyuanyuan.sunnyweather.util.OK
import kotlinx.coroutines.Dispatchers
import retrofit2.await
import java.io.IOException
import java.lang.Exception

object Repository {
    fun loadPlaceList(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val response = ServiceCreator.create<PlaceService>().getPlaceList(query).await()
            if (OK == response.status) Result.success(response.places)
            else Result.failure(IOException(WeatherApp.getAppContext().getString(R.string.server_response_failed)))
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}