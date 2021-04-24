package com.luyuanyuan.sunnyweather.model.http

import com.luyuanyuan.sunnyweather.util.WEATHER_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private val mRetrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceCls: Class<T>): T = mRetrofit.create(serviceCls)

    inline fun <reified T> create(): T = create(T::class.java)
}