package com.argel.weathertraker.core.data.source

import com.argel.weathertraker.core.data.dto.Suggestion
import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.domain.repository.PlaceRepository
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PlaceRepositoryImpl @Inject constructor(
    private val placesClient: PlacesClient
): PlaceRepository {

    override suspend fun searchPlaces(query: String): Either<Failure, List<Suggestion>> = suspendCoroutine { cont ->
        val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                val result = response.autocompletePredictions.map {
                    Suggestion(it.placeId, it.getPrimaryText(null).toString(), it.getSecondaryText(null).toString(), it.getFullText(null).toString())
                }
                cont.resume(Either.Right(result))
            }
            .addOnFailureListener { cont.resume(Either.Left(Failure.ErrorException(it))) }
    }
}