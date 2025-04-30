package com.argel.weathertraker.domain.repository

import com.argel.weathertraker.data.dto.Suggestion
import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.google.android.gms.maps.model.LatLng

interface PlaceRepository {
    suspend fun searchPlaces(query: String): Either<Failure, List<Suggestion>>
    suspend fun getLatLngFromPlaceId(placeId: String): Either<Failure, LatLng>
}