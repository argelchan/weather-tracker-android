package com.argel.weathertraker.core.presentation

sealed class BaseViewState {
    data object ShowLoading : BaseViewState()
    data object HideLoading : BaseViewState()
    data object ShowPlaceHolder : BaseViewState()
    data object HidePlaceHolder : BaseViewState()
}