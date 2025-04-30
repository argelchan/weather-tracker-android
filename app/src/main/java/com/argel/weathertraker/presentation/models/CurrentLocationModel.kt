package com.argel.weathertraker.presentation.models

data class CurrentLocationModel(
    val placeId: String,
    val populated: String,
    val country: String
)