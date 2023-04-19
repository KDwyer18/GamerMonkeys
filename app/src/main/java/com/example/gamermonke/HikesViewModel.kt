package com.example.gamermonke

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HikesViewModel(app: Application) : AndroidViewModel(app) {
    private val bmrData = MutableLiveData<BMR>()
    private var bmrVal = 0.0

    fun setBMR(bmr: Double) {
        bmrVal = bmr
    }

    fun getBMR():Double{
        return bmrVal
    }

    val data: LiveData<BMR>
        get() = bmrData
}