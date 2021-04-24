package com.luyuanyuan.sunnyweather.model.entity

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val query: String, val places: List<Place>)

data class Place(
    val name: String, @SerializedName("formatted_address") val address: String,
    val location: Location
)

data class Location(val lat: String, val lng: String)