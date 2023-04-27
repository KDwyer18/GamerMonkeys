package com.example.gamermonke

import androidx.annotation.WorkerThread
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.Throws

object NetworkUtils {
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q="
    private const val APPIDQUERY = "&appid="
    private const val app_id = "99ea8382701bd7481e5ea568772f739a"
    fun buildURLFromString(location: String): URL? {
        var myURL: URL? = null
        try {
            myURL = URL(BASE_URL + location + APPIDQUERY + app_id)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return myURL
    }

    @WorkerThread
    fun getDataFromURL(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        return try {
            val inputStream = urlConnection.inputStream

            //The scanner trick: search for the next "beginning" of the input stream
            //No need to user BufferedReader
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")
            val hasInput = scanner.hasNext()
            if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}