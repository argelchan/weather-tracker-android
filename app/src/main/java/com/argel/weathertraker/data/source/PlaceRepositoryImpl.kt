package com.argel.weathertraker.data.source

import com.argel.weathertraker.BuildConfig
import com.argel.weathertraker.data.dto.Suggestion
import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.data.dto.LocationResponse
import com.argel.weathertraker.domain.repository.PlaceRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PlaceRepositoryImpl @Inject constructor(
    private val placesClient: PlacesClient
): PlaceRepository {

    override suspend fun searchPlaces(query: String): Either<Failure, List<Suggestion>> =
        suspendCoroutine { cont ->
            val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val result = response.autocompletePredictions.map {
                        Suggestion(
                            it.placeId,
                            it.getPrimaryText(null).toString(),
                            it.getSecondaryText(null).toString(),
                            it.getFullText(null).toString()
                        )
                    }
                    cont.resume(Either.Right(result))
                }
                .addOnFailureListener { cont.resume(Either.Left(Failure.ErrorException(it))) }
        }

    override suspend fun getLatLngFromPlaceId(placeId: String): Either<Failure, LatLng> =
        suspendCoroutine { cont ->
            val placeFields = listOf(Place.Field.LAT_LNG)
            val request = FetchPlaceRequest.builder(placeId, placeFields).build()

            placesClient.fetchPlace(request)
                .addOnSuccessListener { response ->
                    val latLng = response.place.latLng
                    if (latLng != null) {
                        cont.resume(Either.Right(latLng))
                    } else {
                        cont.resume(Either.Left(Failure.ErrorException(Exception("No se encontró lat/lon"))))
                    }
                }
                .addOnFailureListener {
                    cont.resume(Either.Left(Failure.ErrorException(it)))
                }
        }

    override suspend fun getLocationByLatLng(
        lat: Double,
        lon: Double
    ): Either<Failure, LocationResponse> = suspendCoroutine { cont ->
        val url = "https://maps.googleapis.com/maps/api/geocode/json" +
                "?latlng=$lat,$lon&key=${BuildConfig.MAPS_API_KEY}&language=es"
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {

            override fun onFailure(request: Request?, e: IOException?) {
                cont.resume(Either.Left(Failure.ErrorException(e?: Exception("Error en la solicitud"))))
            }

            override fun onResponse(response: Response?) {
                try {
                    val body = response?.body()?.string() ?: throw IOException("Respuesta vacía")
                    val json = JSONObject(body)
                    val results = json.getJSONArray("results")

                    if (results.length() == 0) {
                        cont.resume(Either.Left(Failure.ErrorException(Exception("No se encontró ubicación"))))
                        return
                    }

                    val firstResult = results.getJSONObject(0)
                    val placeId = firstResult.getString("place_id")

                    val addressComponents = firstResult.getJSONArray("address_components")
                    var populate = ""
                    var country = ""

                    for (i in 0 until addressComponents.length()) {
                        val component = addressComponents.getJSONObject(i)
                        val types = component.getJSONArray("types")
                        for (j in 0 until types.length()) {
                            when (types.getString(j)) {
                                "locality" -> populate = component.getString("long_name")
                                "country" -> country = component.getString("long_name")
                            }
                        }
                        if (country.isNotEmpty()) break
                    }

                    cont.resume(Either.Right(LocationResponse(placeId, populate, country)))
                } catch (e: Exception) {
                    cont.resume(Either.Left(Failure.ErrorException(e)))
                }
            }
        })

    }
}