package com.argel.weathertraker.domain.repository

import com.argel.weathertraker.core.data.dto.Suggestion
import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either

interface PlaceRepository {
    suspend fun searchPlaces(query: String): Either<Failure, List<Suggestion>>
}