package com.goodweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.goodweather.android.logic.Repository
import com.goodweather.android.logic.model.Place

class PlaceViewModel: ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    // 用于数据缓存，保证手机屏幕发生旋转时数据不会丢失
    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData){ query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}
