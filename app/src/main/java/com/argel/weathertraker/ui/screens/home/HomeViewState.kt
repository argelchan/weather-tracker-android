package com.argel.weathertraker.ui.screens.home

import com.argel.weathertraker.presentation.models.SuggestModel

sealed class HomeViewState {
    data class Success(val data: List<SuggestModel>): HomeViewState()
}