package com.example.gamermonke

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BMRViewModel(app: Application) : AndroidViewModel(app) {
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

