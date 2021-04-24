package com.luyuanyuan.sunnyweather.util

import android.content.Context
import android.widget.Toast
import com.luyuanyuan.sunnyweather.WeatherApp

fun String.showToast(
    context: Context = WeatherApp.getAppContext(),
    duration: Int = Toast.LENGTH_SHORT
): Toast {
    val toast = Toast.makeText(context, this, duration)
    toast.show()
    return toast
}

fun Int.showToast(
    context: Context = WeatherApp.getAppContext(),
    duration: Int = Toast.LENGTH_SHORT
): Toast = context.getString(this).showToast(context, duration)