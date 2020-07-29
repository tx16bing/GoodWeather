package com.goodweather.android.logic.network

import com.goodweather.android.GoodWeatherApplication
import com.goodweather.android.logic.model.DailyResponse
import com.goodweather.android.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 天气获取API
 */
interface WeatherService {
    @GET("v2.5/${GoodWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealTimeResponse>
    @GET("v2.5/${GoodWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}