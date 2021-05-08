package com.luyuanyuan.sunnyweather.model.entity

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status: String, val result: RealtimeResult)

data class RealtimeResult(val realtime: Realtime)

data class Realtime(
    val temperature: String,
    val skycon: String, @SerializedName("air_quality") val airQuality: AirQuality
)

data class AirQuality(val aqi: Aqi)

data class Aqi(val chn: Float)