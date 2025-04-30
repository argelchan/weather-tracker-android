package com.argel.weathertraker.ui.screens.home

import com.argel.weathertraker.presentation.models.SuggestModel
import com.argel.weathertraker.presentation.models.WeatherModel

sealed class HomeViewState {
    data class Success(val data: List<SuggestModel>): HomeViewState()
    data class SuccessWeather(val data: WeatherModel): HomeViewState()
}