package com.example.gamermonke

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val ARG_LOCATION = "location"
class Weather : Fragment() {
    var location: String? = ""

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            location = it.getString(ARG_LOCATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(location.isNullOrBlank())
            location = "Salt Lake City, Utah"
        viewModel!!.setLocation(location!!)
        val fragmentView = inflater.inflate(R.layout.fragment_weather, container, false)
        viewModel.getWeather(location!!).observe(viewLifecycleOwner, Observer { weatherData ->
            showWeatherData(weatherData)
        })
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showWeatherData(weather: WeatherData) {
        requireView().findViewById<TextView>(R.id.address).text = weather.address
        requireView().findViewById<TextView>(R.id.tv_updated_time).text = weather.updatedAtText
        requireView().findViewById<TextView>(R.id.status).text = weather.status.capitalize()
        requireView().findViewById<TextView>(R.id.temp).text = "${weather.temp}"
        requireView().findViewById<TextView>(R.id.tv_min_temp).text = "${weather.tempMin}"
        requireView().findViewById<TextView>(R.id.tv_max_temp).text = "${weather.tempMax}"
        requireView().findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(weather.sunrise * 1000))
        requireView().findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(weather.sunset * 1000))
        requireView().findViewById<TextView>(R.id.wind).text = "${weather.windSpeed} m/s"
        requireView().findViewById<TextView>(R.id.pressure).text = "${weather.pressure} hPa"
        requireView().findViewById<TextView>(R.id.humidity).text = "${weather.humidity}%"
        requireView().findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
        requireView().findViewById<RelativeLayout>(R.id.main_container).visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance(in_location: String?) =
            Weather().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOCATION, in_location)
                    location = in_location
                }
            }
    }
}

/*
WORKING COROUTINE VERSION
package com.example.gamermonke

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_LOCATION = "location"

/**
* A simple [Fragment] subclass.
* Use the [Weather.newInstance] factory method to
* create an instance of this fragment.
*/
class Weather() : Fragment() {

//    private var location: String? = in_location
   private var location: String? = ""
   val API: String = "9ff22c60ea17c990ff4447c2d37d14d4"

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       arguments?.let {
           location = it.getString(ARG_LOCATION)
       }
   }
   override fun onCreateView(
           inflater: LayoutInflater, container: ViewGroup?,
           savedInstanceState: Bundle?
       ): View? {
           // Inflate the layout for this fragment
           if (location.isNullOrBlank())
               location = "Salt Lake City, Utah"
           val fragmentView = inflater.inflate(R.layout.fragment_weather, container, false)

           // Create a coroutine scope
           val coroutineScope = CoroutineScope(Dispatchers.Main)

           // Define the weatherTask as a suspend function
           suspend fun weatherTask(): String? = withContext(Dispatchers.IO) {
               var response: String?
               try {
                   response =
                       URL("https://api.openweathermap.org/data/2.5/weather?q=$location&units=metric&appid=$API")
                           .readText(Charsets.UTF_8)
               } catch (e: Exception) {
                   response = null
               }
               response
           }

           coroutineScope.launch {
               try {
                   // Execute the weatherTask in the coroutine scope
                   val result = weatherTask()

                   val jsonObj = JSONObject(result)
                   val main = jsonObj.getJSONObject("main")
                   val sys = jsonObj.getJSONObject("sys")
                   val wind = jsonObj.getJSONObject("wind")
                   val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                   val updatedAt: Long = jsonObj.getLong("dt")
                   val updatedAtText =
                       "Updated at: " + SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US).format(
                           Date(updatedAt * 1000)
                       )
                   val temp = main.getString("temp") + "°C"
                   val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                   val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                   val pressure = main.getString("pressure")
                   val humidity = main.getString("humidity")
                   val sunrise: Long = sys.getLong("sunrise")
                   val sunset: Long = sys.getLong("sunset")
                   val timezone = jsonObj.getInt("timezone")
                   val windSpeed = wind.getString("speed")
                   val weatherDescription = weather.getString("description")
                   val address = jsonObj.getString("name") + ", " + sys.getString("country")

                   fragmentView.findViewById<TextView>(R.id.address).text = address
                   fragmentView.findViewById<TextView>(R.id.tv_updated_time).text = updatedAtText
                   fragmentView.findViewById<TextView>(R.id.status).text =
                       weatherDescription.capitalize()
                   fragmentView.findViewById<TextView>(R.id.temp).text = temp
                   fragmentView.findViewById<TextView>(R.id.tv_min_temp).text = tempMin
                   fragmentView.findViewById<TextView>(R.id.tv_max_temp).text = tempMax
                   fragmentView.findViewById<TextView>(R.id.sunrise).text =
                       SimpleDateFormat("hh:mm a", Locale.US).format(Date(sunrise * 1000))
                   fragmentView.findViewById<TextView>(R.id.sunset).text =
                       SimpleDateFormat("hh:mm a", Locale.US).format(Date(sunset * 1000))
                   fragmentView.findViewById<TextView>(R.id.wind).text = windSpeed
                   fragmentView.findViewById<TextView>(R.id.pressure).text = pressure
                   fragmentView.findViewById<TextView>(R.id.humidity).text = humidity

                   fragmentView.findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                   fragmentView.findViewById<RelativeLayout>(R.id.main_container).visibility =
                       View.VISIBLE
               } catch (e: Exception) {
                   fragmentView.findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                   fragmentView.findViewById<TextView>(R.id.error_warning).visibility =
                       View.VISIBLE
               }
           }

           return fragmentView
       }

           companion object {
       /**
        * Use this factory method to create a new instance of
        * this fragment using the provided parameters.
        *
        * @param param1 Parameter 1.
        * @param param2 Parameter 2.
        * @return A new instance of fragment Weather.
        */
       // TODO: Rename and change types and number of parameters
       @JvmStatic
       fun newInstance(in_location: String?) =
           Weather().apply {
               arguments = Bundle().apply {
                   putString(ARG_LOCATION, in_location)
                   location = in_location
               }
           }
   }
}

 */


