package com.argel.weathertraker.data.source

import com.argel.weathertraker.BuildConfig
import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.core.platform.NetworkHandler
import com.argel.weathertraker.data.api.OpenWeatherApi
import com.argel.weathertraker.data.dto.Coord
import com.argel.weathertraker.data.dto.Main
import com.argel.weathertraker.data.dto.Sys
import com.argel.weathertraker.data.dto.WeatherResponse
import com.argel.weathertraker.data.dto.Wind
import com.argel.weathertraker.domain.repository.WeatherRepository
import com.argel.weathertraker.framework.api.ApiRequest
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val api: OpenWeatherApi
): WeatherRepository, ApiRequest {

    override suspend fun getWeather(
        lat: Double,
        lon: Double
    ): Either<Failure, WeatherResponse> {
        return makeRequestSuspend(
            networkHandler,
            { api.getWeatherByCoordinates(lat, lon, BuildConfig.openWeatherApiKey) },
            {
                it
            },
            WeatherResponse(
                "",
                Main(0.0, 0.0, 0),
                0,
                listOf(),
                Sys(0, 0),
                Wind(0.0),
                Coord(0.0, 0.0)
            )
        )
    }
}