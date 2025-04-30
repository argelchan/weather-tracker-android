package com.argel.weathertraker.data.dto

import com.argel.weathertraker.core.extension.toFormattedTime
import com.argel.weathertraker.presentation.models.CurrentWeatherModel
import com.argel.weathertraker.presentation.models.LocationModel
import com.argel.weathertraker.presentation.models.SysModel
import com.argel.weathertraker.presentation.models.WeatherModel
import kotlinx.serialization.SerialName

data class WeatherResponse(
    @SerialName("name") val name: String,
    @SerialName("main") val main: Main,
    @SerialName("humidity") val humidity: Int,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("sys") val sys: Sys,
    @SerialName("wind") val wind: Wind,
    @SerialName("coord") val coord: Coord
) {
    fun toModel() = WeatherModel(
        name = name,
        temp = main.temp,
        feelsLike = main.feelsLike,
        humidity = main.humidity,
        weather = weather.map { it.toModel() },
        sys = sys.toModel(),
        speed = wind.speed,
        location = coord.toModel())
}

data class Coord(
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double
) {
    fun toModel() = LocationModel(
        lat = lat,
        lon = lon
    )
}

data class Wind(
    @SerialName("speed") val speed: Double
)

data class Main(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("humidity") val humidity: Int
)

data class Weather(
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
) {
    fun toModel() = CurrentWeatherModel(
        main = main,
        description = description,
        icon = icon
    )
}

data class Sys(
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long
) {
    fun toModel() = SysModel(
        sunrise = sunrise.toFormattedTime(),
        sunset = sunset.toFormattedTime()
    )
}

data class WeatherRequest(
    val id: String,
    val country: String
)