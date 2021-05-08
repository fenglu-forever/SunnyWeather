package com.luyuanyuan.sunnyweather.model.http

import com.luyuanyuan.sunnyweather.model.entity.DailyResponse
import com.luyuanyuan.sunnyweather.model.entity.RealtimeResponse
import com.luyuanyuan.sunnyweather.util.TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.5/${TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}