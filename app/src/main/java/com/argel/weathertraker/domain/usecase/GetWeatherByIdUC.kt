package com.argel.weathertraker.domain.usecase

import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.core.functional.flatMap
import com.argel.weathertraker.core.interactor.UseCase
import com.argel.weathertraker.data.dto.WeatherRequest
import com.argel.weathertraker.domain.repository.PlaceRepository
import com.argel.weathertraker.domain.repository.WeatherRepository
import com.argel.weathertraker.presentation.models.WeatherModel
import javax.inject.Inject

class GetWeatherByIdUC @Inject constructor(
    private val repository: WeatherRepository,
    private val placeRepository: PlaceRepository
): UseCase<WeatherModel, WeatherRequest>() {
    override suspend fun run(params: WeatherRequest): Either<Failure, WeatherModel> {
        val result = placeRepository.getLatLngFromPlaceId(params.id)
        if (result is Either.Left) return result
        val latLng = (result as Either.Right).b
        return repository.getWeather(latLng.latitude, latLng.longitude).flatMap { Either.Right(it.toModel()) }
    }
}