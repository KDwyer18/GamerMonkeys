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

    private var location: String? = in_location
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
        val fragmentView = inflater.inflate(R.layout.fragment_weather, container, false)
        class weatherTask() : AsyncTask<String, Void, String>()
        {
            override fun onPreExecute() {
                super.onPreExecute()
                fragmentView.findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
                fragmentView.findViewById<RelativeLayout>(R.id.main_container).visibility = View.GONE
                fragmentView.findViewById<TextView>(R.id.error_warning).visibility = View.GONE
            }
            override fun doInBackground(vararg p0: String?): String? {
                var response:String?
                try {
                    response = URL("https://api.openweathermap.org/data/2.5/weather?q=$location&units=metric&appid=$API")
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
                    val updatedAt:Long = jsonObj.getLong("dt")
                    val updatedAtText = "Updated at: " + SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US).format(Date(updatedAt*1000))
                    val temp = main.getString("temp") + "°C"
                    val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                    val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                    val pressure = main.getString("pressure")
                    val humidity = main.getString("humidity")
                    val sunrise:Long = sys.getLong("sunrise")
                    val sunset:Long = sys.getLong("sunset")
                    val timezone = jsonObj.getInt("timezone")
                    val windSpeed = wind.getString("speed")
                    val weatherDescription = weather.getString("description")
                    val address = jsonObj.getString("name") + ", " + sys.getString("country")

                    fragmentView.findViewById<TextView>(R.id.address).text = address
                    fragmentView.findViewById<TextView>(R.id.tv_updated_time).text = updatedAtText
                    fragmentView.findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                    fragmentView.findViewById<TextView>(R.id.temp).text = temp
                    fragmentView.findViewById<TextView>(R.id.tv_min_temp).text = tempMin
                    fragmentView.findViewById<TextView>(R.id.tv_max_temp).text = tempMax
                    fragmentView.findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(sunrise*1000))
                    fragmentView.findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.US).format(Date(sunset*1000))
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

        }
        weatherTask().execute()
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
            Weather(in_location).apply {
                arguments = Bundle().apply {
                    putString(ARG_LOCATION, in_location)
                }
            }
    }
}