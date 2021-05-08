package com.luyuanyuan.sunnyweather.model.http

import android.util.Log
import com.luyuanyuan.sunnyweather.util.WEATHER_BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

object ServiceCreator {
    const val TAG = "ServiceCreator"

    private val mClient = OkHttpClient.Builder().apply {
        hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean = true

        })
        interceptors()?.clear()
        addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val url = request.url()
                val path = url.encodedPath()
                Log.d(TAG, "path = " + path)
                val query = url.query()
                Log.d(TAG, "query = " + query)
                val requestNew = chain.request().newBuilder().build()
                return chain.proceed(requestNew)
            }
        })
        connectTimeout(10, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)
    }.build()

    private val mRetrofit = Retrofit.Builder()
        .client(mClient)
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceCls: Class<T>): T = mRetrofit.create(serviceCls)

    inline fun <reified T> create(): T = create(T::class.java)
}