package com.luyuanyuan.sunnyweather.util

import android.view.View
import android.view.Window
import android.view.WindowManager
import com.luyuanyuan.sunnyweather.WeatherApp

const val FLAG_BASE_BAR = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

const val FLAG_EXPAND_STATUS_BAR =
    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            FLAG_BASE_BAR

fun Window.expandStatusBar() {
    decorView.systemUiVisibility =
        FLAG_EXPAND_STATUS_BAR
}

fun Window.updateStatusBarColor(color: Int) {
    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    statusBarColor = color
}

fun getStatusBarHeight(): Int {
    val res = WeatherApp.getAppContext().resources
    val resId = res.getIdentifier("status_bar_height", "dimen", "android")
    if (resId != 0) {
        return res.getDimensionPixelSize(resId)
    }
    return 0
}

fun getNavigationBarHeight(): Int {
    val res = WeatherApp.getAppContext().resources
    val resId = res.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resId != 0) {
        return res.getDimensionPixelSize(resId)
    }
    return 0
}