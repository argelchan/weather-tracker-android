package com.argel.weathertraker.core.data.dto

import com.argel.weathertraker.presentation.models.SuggestModel

data class Suggestion(
    val placeId: String,
    val description: String
) {
    fun toModel() = SuggestModel(
        id = placeId,
        name = description
    )

}