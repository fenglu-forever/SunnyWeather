package com.luyuanyuan.sunnyweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.luyuanyuan.sunnyweather.model.Repository
import com.luyuanyuan.sunnyweather.model.entity.Place

class PlaceViewModel : ViewModel() {
    private val mSearchPlaceData = MutableLiveData<String>()

    val mPlaceList = ArrayList<Place>()

    val mPlaceListData = Transformations.switchMap(mSearchPlaceData) {
        Repository.loadPlaceList(it)
    }

    fun searchPlaceList(query: String) {
        mSearchPlaceData.value = query
    }
}