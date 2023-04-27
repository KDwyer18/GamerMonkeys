package com.example.gamermonke

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Embedded

@Entity(tableName = "weather_data")
data class WeatherData(
    @field:ColumnInfo(name = "location_data")
    var locationData: LocationData? = null,
    @field:Embedded(prefix = "current_condition_")
    var currentCondition: CurrentCondition = CurrentCondition(),
    @field:Embedded(prefix = "temperature_")
    var temperature: Temperature = Temperature(),
    @field:Embedded(prefix = "wind_")
    var wind: Wind = Wind(),
    @field:Embedded(prefix = "rain_")
    var rain: Rain = Rain(),
    @field:Embedded(prefix = "snow_")
    var snow: Snow = Snow(),
    @field:Embedded(prefix = "clouds_")
    var clouds: Clouds = Clouds()
) {

    data class CurrentCondition(
        @field:ColumnInfo(name = "weather_id")
        var weatherId: Long = 0,
        @field:ColumnInfo(name = "condition")
        var condition: String? = null,
        @field:ColumnInfo(name = "description")
        var description: String? = null,
        @field:ColumnInfo(name = "icon")
        var icon: String? = null,
        @field:ColumnInfo(name = "pressure")
        var pressure: Double = 0.0,
        @field:ColumnInfo(name = "humidity")
        var humidity: Double = 0.0
    )

    data class Temperature(
        @field:ColumnInfo(name = "temp")
        var temp: Double = 0.0,
        @field:ColumnInfo(name = "min_temp")
        var minTemp: Double = 0.0,
        @field:ColumnInfo(name = "max_temp")
        var maxTemp: Double = 0.0
    )

    data class Wind(
        @field:ColumnInfo(name = "speed")
        var speed: Double = 0.0,
        @field:ColumnInfo(name = "deg")
        var deg: Double = 0.0
    )

    data class Rain(
        @field:ColumnInfo(name = "time")
        var time: String? = null,
        @field:ColumnInfo(name = "amount")
        var amount: Double = 0.0
    )

    data class Snow(
        @field:ColumnInfo(name = "time")
        var time: String? = null,
        @field:ColumnInfo(name = "amount")
        var amount: Double = 0.0
    )

    data class Clouds(
        @field:ColumnInfo(name = "percentage")
        var percentage: Long = 0
    )

}
