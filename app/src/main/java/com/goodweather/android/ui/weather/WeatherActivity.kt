package com.goodweather.android.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goodweather.android.R

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }
}