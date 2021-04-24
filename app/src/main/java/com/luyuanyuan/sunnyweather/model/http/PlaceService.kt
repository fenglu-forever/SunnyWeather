package com.luyuanyuan.sunnyweather.model.http

import com.luyuanyuan.sunnyweather.util.TOKEN
import com.luyuanyuan.sunnyweather.model.entity.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=$TOKEN&lang=zh_CN")
    fun getPlaceList(@Query("query") query: String): Call<PlaceResponse>
}