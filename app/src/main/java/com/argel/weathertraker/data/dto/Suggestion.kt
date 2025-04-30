package com.argel.weathertraker.data.dto

import com.argel.weathertraker.presentation.models.SuggestModel

data class Suggestion(
    val placeId: String,
    val primaryName: String,
    val secondaryName: String,
    val fullName: String
) {
    fun toModel() = SuggestModel(
        id = placeId,
        name = primaryName,
        description = fullName
    )

}