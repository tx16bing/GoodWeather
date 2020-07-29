package com.goodweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.goodweather.android.logic.Repository
import com.goodweather.android.logic.model.Location

class WeatherViewModel: ViewModel(){

    // 监听该数据变化参数
    private val locationLiveData = MutableLiveData<Location>()

    // 用于缓存，横竖变化时候保证数据不丢失
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    // 提供给外部访问的方法，修改locationLiveData信息，会触发weatherLiveData执行Repository.refreshWeather(...)
    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }
}