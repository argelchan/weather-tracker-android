package com.argel.weathertraker.domain.usecase

import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.core.functional.flatMap
import com.argel.weathertraker.core.interactor.UseCase
import com.argel.weathertraker.data.dto.LocationRequest
import com.argel.weathertraker.domain.repository.PlaceRepository
import com.argel.weathertraker.presentation.models.CurrentLocationModel
import javax.inject.Inject

class GetPlaceIdByLocationUC @Inject constructor(
    private val placeRepository: PlaceRepository
): UseCase<CurrentLocationModel, LocationRequest>() {
    override suspend fun run(params: LocationRequest): Either<Failure, CurrentLocationModel> {
        return placeRepository.getLocationByLatLng(params.lat, params.lon).flatMap {
            Either.Right(it.toModel()) }
    }
}
