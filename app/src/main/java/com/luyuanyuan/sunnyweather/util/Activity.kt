package com.luyuanyuan.sunnyweather.util

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    if (!(this is Activity)) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

inline fun <reified T> Context.startActivity(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    if (!(this is Activity)) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    intent.block()
    startActivity(intent)
}