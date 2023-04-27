package com.example.gamermonke

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlin.jvm.Volatile
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


abstract class WeatherRoom : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    // Make the db singleton. Could in theory
    // make this an object class, but the companion object approach
    // is nicer (imo)
    companion object {
        @Volatile
        private var mInstance: WeatherRoom? = null
        fun getDatabase(
            context: Context,
            scope : CoroutineScope
        ): WeatherRoom {
            return mInstance?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherRoom::class.java, "weather.db"
                )
                    .addCallback(RoomDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                mInstance = instance
                instance
            }
        }

        private class RoomDatabaseCallback(
            private val scope: CoroutineScope
        ): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                mInstance?.let { database ->
                    scope.launch(Dispatchers.IO){
                        populateDbTask(database.weatherDao())
                    }
                }
            }
        }

        suspend fun populateDbTask(weatherDao: WeatherDao) {
            val WeatherData = WeatherData(
                LocationData(),
                WeatherData.CurrentCondition(),
                WeatherData.Temperature(),
                WeatherData.Wind(),
                WeatherData.Rain(),
                WeatherData.Snow(),
                WeatherData.Clouds()
            )
            weatherDao.insert(WeatherData)
        }
    }
}