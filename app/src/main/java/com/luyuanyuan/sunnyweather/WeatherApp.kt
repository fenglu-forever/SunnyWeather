package com.luyuanyuan.sunnyweather

import android.app.Application
import android.content.Context

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        mAppContext = this
    }

    companion object {
        private lateinit var mAppContext: Context
        fun getAppContext(): Context = mAppContext
    }
}