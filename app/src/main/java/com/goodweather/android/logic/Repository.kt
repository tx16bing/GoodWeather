package com.goodweather.android.logic

import androidx.lifecycle.liveData
import com.goodweather.android.logic.model.Place
import com.goodweather.android.logic.network.GoodWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    // 网络请求需要IO(子线程)线程
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = GoodWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
    
}