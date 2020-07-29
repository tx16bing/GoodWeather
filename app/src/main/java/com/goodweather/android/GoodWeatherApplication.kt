package com.goodweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class GoodWeatherApplication: Application(){

    companion object {
        // 彩云API 令牌
        const val TOKEN = "oWVBKSD0bf8xw6kz"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}