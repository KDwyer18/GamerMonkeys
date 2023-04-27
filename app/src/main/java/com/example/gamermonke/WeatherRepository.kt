package com.example.gamermonke

import androidx.lifecycle.MutableLiveData
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.jvm.Synchronized

class WeatherRepository private constructor(weatherDao: WeatherDao) {
    // This LiveData object that is notified when we've fetched the weather
    val data = MutableLiveData<WeatherData>()

    // This flow is triggered when any change happens to the database
    val allCityWeatherData: Flow<List<WeatherData>> = weatherDao.getAllWeather()

    private var mLocation: String? = null
    private var mJsonString: String? = null
    private var mWeatherDao: WeatherDao = weatherDao

    fun setLocation(location: String) {
        // First cache the location
        mLocation = location

        // Everything within the scope happens logically sequentially
        mScope.launch(Dispatchers.IO){
            //fetch data on a worker thread
            fetchAndParseWeatherData(location)

            // After the suspend function returns, Update the View THEN insert into db
            if(mJsonString!=null) {
                // Populate live data object. But since this is happening in a background thread (the coroutine),
                // we have to use postValue rather than setValue. Use setValue if update is on main thread
                data.postValue( JSONWeatherUtils.getWeatherData(mJsonString))

                // insert into db. This will trigger a flow
                // that updates a recyclerview. All db ops should happen
                // on a background thread
                val locationData = LocationData()
                val weatherData = JSONWeatherUtils.getWeatherData(mJsonString) ?: WeatherData()
                weatherData.locationData = locationData
                mWeatherDao.insert(weatherData)
            }
        }
    }

    @WorkerThread
    suspend fun insert(weatherData: WeatherData) {
        if (weatherData.locationData != null) {
            val weatherData = WeatherData()
            weatherData.locationData = LocationData()
            weatherData.currentCondition = WeatherData.CurrentCondition(
                weatherId = 800,
                condition = "Clear",
                description = "clear sky",
                icon = "01d",
                pressure = 1012.0,
                humidity = 44.0
            )
            weatherData.temperature = WeatherData.Temperature(
                temp = 22.5,
                minTemp = 20.0,
                maxTemp = 25.0
            )
            weatherData.wind = WeatherData.Wind(
                speed = 5.0,
                deg = 180.0
            )
            weatherData.rain = WeatherData.Rain(
                time = "2023-04-26 12:00:00",
                amount = 0.0
            )
            weatherData.snow = WeatherData.Snow(
                time = "2023-04-26 12:00:00",
                amount = 0.0
            )
            weatherData.clouds = WeatherData.Clouds(
                percentage = 0
            )

            mWeatherDao.insert(weatherData)
        }
    }

    @WorkerThread
    suspend fun fetchAndParseWeatherData(location: String) {
        val weatherDataURL = NetworkUtils.buildURLFromString(location)
        if(weatherDataURL!=null) {
            // This is actually a blocking call unless you're using an
            // asynchronous IO library (which we're not). However, it is a blocking
            // call on a background thread, not on the UI thread
            val jsonWeatherData = NetworkUtils.getDataFromURL(weatherDataURL)
            if (jsonWeatherData != null) {
                mJsonString = jsonWeatherData
            }
        }
    }

    // Make the repository singleton. Could in theory
    // make this an object class, but the companion object approach
    // is nicer (imo)
    companion object {
        private var mInstance: WeatherRepository? = null
        private lateinit var mScope: CoroutineScope
        @Synchronized
        fun getInstance(weatherDao: WeatherDao,
                        scope: CoroutineScope
        ): WeatherRepository {
            mScope = scope
            return mInstance?: synchronized(this){
                val instance = WeatherRepository(weatherDao)
                mInstance = instance
                instance
            }
        }
    }
}