package com.argel.weathertraker.presentation.models


data class WeatherModel(
    val name: String,
    val temp: Double,
    val feelsLike: Double,
    val humidity: Int,
    val weather: List<CurrentWeatherModel>,
    val sys: SysModel,
    val speed: Double,
    val location: LocationModel
)

data class CurrentWeatherModel(
    val main: String,
    val description: String,
    val icon: String
)

data class SysModel(
    val sunrise: String,
    val sunset: String
)

data class LocationModel(
    val lat: Double,
    val lon: Double
)