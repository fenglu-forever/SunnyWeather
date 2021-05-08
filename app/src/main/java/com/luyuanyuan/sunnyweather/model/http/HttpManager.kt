package com.luyuanyuan.sunnyweather.model.http

import com.luyuanyuan.sunnyweather.R
import com.luyuanyuan.sunnyweather.WeatherApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Call<T>.requestNetwork(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(
                    IOException(
                        WeatherApp
                            .getAppContext()
                            .resources
                            .getString(R.string.server_exception)
                    )
                )
            }
        })
    }
}