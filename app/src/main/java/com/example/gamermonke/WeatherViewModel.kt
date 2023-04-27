package com.example.gamermonke

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WeatherViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepository()

    private var location = ""

    fun setLocation(loc: String) {
        location = loc
    }
    fun getWeather(location: String): MutableLiveData<WeatherData> {
        val weatherData: MutableLiveData<WeatherData> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = weatherRepository.getWeather(location)
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt: Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: " + SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US).format(Date(updatedAt * 1000))
                val temp = main.getString("temp") + "°F"
                val tempMin = "Min Temp: " + main.getString("temp_min") + "°F"
                val tempMax = "Max Temp: " + main.getString("temp_max") + "°F"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val timezone = jsonObj.getInt("timezone")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name") + ", " + sys.getString("country")
                weatherData.postValue(WeatherData(address, updatedAtText, weatherDescription.capitalize(),
                    temp, tempMin, tempMax, sunrise, sunset, windSpeed, pressure, humidity))
            } catch (e: Exception) {
                weatherData.postValue(null)
            }
        }
        return weatherData
    }
}

class WeatherRepository {

    private val API: String = "9ff22c60ea17c990ff4447c2d37d14d4"

    suspend fun getWeather(location: String): String? = suspendCoroutine { continuation ->
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$location&units=imperial&appid=$API"
        val client = URL(url).readText()
        if (client != null) {
            continuation.resume(client)
        } else {
            continuation.resumeWithException(Exception("Error fetching weather data"))
        }
    }
}

data class WeatherData(val address: String, val updatedAtText: String, val status: String,
                       val temp: String, val tempMin: String, val tempMax: String, val sunrise: Long,
                       val sunset: Long, val windSpeed: String, val pressure: String, val humidity: String)


