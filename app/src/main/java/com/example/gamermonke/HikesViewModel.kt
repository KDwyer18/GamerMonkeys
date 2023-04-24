package com.example.gamermonke

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HikesViewModel(app: Application) : AndroidViewModel(app) {
    private val hikesData = MutableLiveData<Hikes>()
    private var location = ""

    fun setLocation(loc: String) {
        location = loc
    }

    val data: LiveData<Hikes>
        get() = hikesData
}