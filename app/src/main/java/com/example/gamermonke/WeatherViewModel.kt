package com.example.gamermonke

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WeatherViewModel(app: Application) : AndroidViewModel(app) {
    private val weatherData = MutableLiveData<BMR>()

    val data: LiveData<BMR>
        get() = weatherData
}