package com.argel.weathertraker.data.dto

import com.argel.weathertraker.presentation.models.CurrentLocationModel

class LocationRequest(
    val lat: Double,
    val lon: Double
)

class LocationResponse(
    val placeId: String,
    val city: String,
    val country: String
) {
    fun toModel() = CurrentLocationModel(
        placeId = placeId,
        populated = city,
        country = country
    )

}