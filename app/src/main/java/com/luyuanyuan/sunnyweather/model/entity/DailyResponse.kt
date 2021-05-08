package com.luyuanyuan.sunnyweather.model.entity

import com.google.gson.annotations.SerializedName

data class DailyResponse(val status: String, val result: DailyResult)

data class DailyResult(val daily: Daily)

data class Daily(
    val temperature: List<Temperature>,
    val skycon: List<Skycon>,
    @SerializedName("life_index") val lifeIndex: LifeIndex
)

data class Temperature(val max: Float, val min: Float)

data class Skycon(val date: String, val value: String)

data class LifeIndex(
    val coldRisk: List<Description>,
    val carWashing: List<Description>,
    val ultraviolet: List<Description>,
    val dressing: List<Description>
)

data class Description(val desc: String)
