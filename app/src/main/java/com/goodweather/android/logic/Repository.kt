package com.goodweather.android.logic

import androidx.lifecycle.liveData
import com.goodweather.android.GoodWeatherApplication
import com.goodweather.android.R
import com.goodweather.android.logic.model.Place
import com.goodweather.android.logic.model.Weather
import com.goodweather.android.logic.network.GoodWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * 仓库层
 * 网络请求需要IO线程
 * 统一入口函数fire()封装try {} catch
 */
object Repository {

    private const val STATUS_OK = "ok"

    // 获取城市信息
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = GoodWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == STATUS_OK) {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    // 获取天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            // 使用async + await 保证两个网络请求都成功后才进一步执行程序
            val deferredRealtime = async {
                GoodWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                GoodWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if (realtimeResponse.status == STATUS_OK && dailyResponse.status == STATUS_OK) {
                val weather = Weather(
                    realtimeResponse.result.realtime,
                    dailyResponse.result.daily
                )
                Result.success(weather)
            } else {
                Result.failure<Weather>(
                    RuntimeException(
                        "real time response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    // 统一入口函数fire()封装try {} catch
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

}