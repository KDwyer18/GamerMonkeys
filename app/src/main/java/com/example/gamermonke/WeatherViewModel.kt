package com.example.gamermonke

import androidx.lifecycle.*

class WeatherViewModel(repository: WeatherRepository) : ViewModel() {
    // Connect a live data object to the current bit of weather info
    private val jsonData: LiveData<WeatherData> = repository.data

    //The singleton repository. If our app maps to one process, the recommended
    // pattern is to make repo and db singletons. That said, it's sometimes useful
    // to have more than one repo so it doesn't become a kitchen sink class, but each
    // of those repos could be singleton.
    private var mWeatherRepository: WeatherRepository = repository

    fun setLocation(location: String) {
        // Simply pass the location to the repository
        mWeatherRepository.setLocation(location)
    }

    // Returns the data contained in the live data object
    val data: LiveData<WeatherData>
        get() = jsonData
}

// This factory class allows us to define custom constructors for the view model
class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}