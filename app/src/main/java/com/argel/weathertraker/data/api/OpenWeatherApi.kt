package com.argel.weathertraker.data.api

import com.argel.weathertraker.data.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Celsius
    ): WeatherResponse
}