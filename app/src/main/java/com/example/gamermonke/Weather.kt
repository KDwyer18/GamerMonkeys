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
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_LOCATION = "location"

/**
 * A simple [Fragment] subclass.
 * Use the [Weather.newInstance] factory method to
 * create an instance of this fragment.
 */
class Weather(in_location: String?) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var fragmentView: View
    private var location: String? = in_location
    var updatedAt : Long = 1
    var temp : String? = null
    var tempMin : String? = null
    var tempMax : String? = null
    var pressure : String? = null
    var humidity : String? = null
    var sunrise: Long = 1
    var sunset: Long = 1
    var windSpeed : String? = null
    var weatherDescription : String? = null
    var address: String? = null
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
        if(location.isNullOrBlank())
            location = "Salt Lake City, Utah"
        fragmentView = inflater.inflate(R.layout.fragment_weather, container, false)
        if(savedInstanceState != null)
        {
            val temp = savedInstanceState.getString("temp")
            val tempMin = savedInstanceState.getString("min_temp")
            val tempMax = savedInstanceState.getString("max_temp")
            val humidity = savedInstanceState.getString("humidity")
            val pressure = savedInstanceState.getString("pressure")
            val updatedAtText = savedInstanceState.getString("updatedAt")
            val windSpeed = savedInstanceState.getString("wind")
            val sunset = savedInstanceState.getLong("sunrise")
            val sunrise = savedInstanceState.getLong("sunset")
            val weatherDescription = savedInstanceState.getString("description")
            val address = savedInstanceState.getString("address")
            fragmentView.findViewById<TextView>(R.id.address).text = address
            fragmentView.findViewById<TextView>(R.id.tv_updated_time).text = updatedAtText
            if (weatherDescription != null) {
                fragmentView.findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
            }
            fragmentView.findViewById<TextView>(R.id.temp).text = temp
            fragmentView.findViewById<TextView>(R.id.tv_min_temp).text = tempMin
            fragmentView.findViewById<TextView>(R.id.tv_max_temp).text = tempMax
            fragmentView.findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(sunrise*1000))
            fragmentView.findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(sunset*1000))
            fragmentView.findViewById<TextView>(R.id.wind).text = windSpeed
            fragmentView.findViewById<TextView>(R.id.pressure).text = pressure
            fragmentView.findViewById<TextView>(R.id.humidity).text = humidity
        }
       // mFetchWeatherTask.setWeakReference(this)


        FetchWeatherTask().execute()



        return fragmentView
    }

    private inner class FetchWeatherTask() : AsyncTask<String, Void, String>()
    {
        var weatherFragmentWeakReference: WeakReference<Weather>? = null
        override fun onPreExecute() {
            super.onPreExecute()
            fragmentView.findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            fragmentView.findViewById<RelativeLayout>(R.id.main_container).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.error_warning).visibility = View.GONE
        }
        override fun doInBackground(vararg p0: String?): String? {
            var response:String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$location&units=imperial&appid=$API")
                    .readText(Charsets.UTF_8)
            }
            catch (e: Exception)
            {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                updatedAt= jsonObj.getLong("dt")
                val updatedAtText = "Updated at: " + SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US).format(Date(
                    updatedAt!! *1000))
                temp = main.getString("temp") + "°F"
                tempMin = "Min Temp: " + main.getString("temp_min")+"°F"
                tempMax = "Max Temp: " + main.getString("temp_max")+"°F"
                pressure = main.getString("pressure")
                humidity = main.getString("humidity")
                sunrise = sys.getLong("sunrise")
                sunset = sys.getLong("sunset")
                val timezone = jsonObj.getInt("timezone")
                windSpeed = wind.getString("speed")
                weatherDescription = weather.getString("description")
                address = jsonObj.getString("name") + ", " + sys.getString("country")

                fragmentView.findViewById<TextView>(R.id.address).text = address
                fragmentView.findViewById<TextView>(R.id.tv_updated_time).text = updatedAtText
                fragmentView.findViewById<TextView>(R.id.status).text = weatherDescription
                fragmentView.findViewById<TextView>(R.id.temp).text = temp
                fragmentView.findViewById<TextView>(R.id.tv_min_temp).text = tempMin
                fragmentView.findViewById<TextView>(R.id.tv_max_temp).text = tempMax
                fragmentView.findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(
                    sunrise!! *1000))
                fragmentView.findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(
                    sunset!! *1000))
                fragmentView.findViewById<TextView>(R.id.wind).text = windSpeed
                fragmentView.findViewById<TextView>(R.id.pressure).text = pressure
                fragmentView.findViewById<TextView>(R.id.humidity).text = humidity

                fragmentView.findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                fragmentView.findViewById<RelativeLayout>(R.id.main_container).visibility = View.VISIBLE
            }
            catch (e:Exception){
                fragmentView.findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                fragmentView.findViewById<TextView>(R.id.error_warning).visibility = View.VISIBLE
            }
        }
        fun setWeakReference(ref: Weather)
        {
            weatherFragmentWeakReference = WeakReference(ref)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("updatedAt", updatedAt)
        outState.putString("temp", temp)
        outState.putString("tempMin", tempMin)
        outState.putString("tempMax", tempMax)
        outState.putString("pressure", pressure)
        outState.putString("humidity", humidity)
        outState.putLong("sunrise", sunrise)
        outState.putLong("sunrise", sunrise)
        outState.putString("windspeed", windSpeed)
        outState.putString("weatherDescription", weatherDescription)
        outState.putString("address", address)

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
        fun newInstance(in_location: String?) = Weather(in_location).apply {
                arguments = Bundle().apply {
                    arguments?.putString(ARG_LOCATION, in_location)
                }
            }
    }
}