package com.argel.weathertraker.core.data.models

import com.google.gson.annotations.SerializedName

data class Pagination<T>(
    @SerializedName("page")
    val pagination: Pag = Pag(),
    val data: List<T> = emptyList(),
)

data class Pag (
    @SerializedName("offset")
    var offset: Int = 0,
    @SerializedName("limit")
    var perPage: Int = 0,
    @SerializedName("total")
    val total: Int = 1,
    @SerializedName("nextPage")
    var nextPage: String = "",
)