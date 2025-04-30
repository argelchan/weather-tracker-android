package com.argel.weathertraker.domain.repository

import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.data.dto.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Either<Failure, WeatherResponse>
}