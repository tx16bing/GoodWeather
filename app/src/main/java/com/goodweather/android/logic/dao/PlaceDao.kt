package com.goodweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.goodweather.android.GoodWeatherApplication
import com.goodweather.android.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

    fun savePlace(place: Place) {
        sharePreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharePreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharePreferences().contains("place")

    private fun sharePreferences() =
        GoodWeatherApplication.context.getSharedPreferences("good_weather", Context.MODE_PRIVATE)

}