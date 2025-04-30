package com.argel.weathertraker.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.argel.weathertraker.core.presentation.BaseViewModel
import com.argel.weathertraker.core.presentation.BaseViewState
import com.argel.weathertraker.data.dto.LocationRequest
import com.argel.weathertraker.data.dto.WeatherRequest
import com.argel.weathertraker.domain.usecase.GetAddressUC
import com.argel.weathertraker.domain.usecase.GetPlaceIdByLocationUC
import com.argel.weathertraker.domain.usecase.GetWeatherByIdUC
import com.argel.weathertraker.presentation.models.CurrentLocationModel
import com.argel.weathertraker.presentation.models.SuggestModel
import com.argel.weathertraker.presentation.models.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAddressUc: GetAddressUC,
    private val weatherByIdUC: GetWeatherByIdUC,
    private val getPlaceIdByLocationUC: GetPlaceIdByLocationUC
) : BaseViewModel() {

    private val _suggestions: MutableLiveData<HomeViewState> = MutableLiveData()
    val suggestions: LiveData<HomeViewState> = _suggestions

    fun searchSuggestion(query: String) {
        getAddressUc(query) { either ->
            either.fold(
                ::handleFailure,
                ::onSearchSuggestionSuccess
            )
        }
    }

    private fun onSearchSuggestionSuccess(data: List<SuggestModel>) {
        _suggestions.value = HomeViewState.Success(data)
    }

    fun getWeatherById(location: WeatherRequest) {
        _state.value = BaseViewState.ShowLoading
        weatherByIdUC(location) { either ->
            _state.value = BaseViewState.HideLoading
            either.fold(
                ::handleFailure,
                ::onGetWeatherByCoordinatesSuccess)
        }
    }

    private fun onGetWeatherByCoordinatesSuccess(data: WeatherModel) {
        _suggestions.value = HomeViewState.SuccessWeather(data)

    }

    fun getMyPlaceId(lat: Double, lon: Double) {
        _state.value = BaseViewState.ShowLoading
        getPlaceIdByLocationUC(LocationRequest(lat, lon)) { either ->
            _state.value = BaseViewState.HideLoading
            either.fold(
                ::handleFailure,
                ::onGetPlaceIdSuccess
            )
        }
    }

    private fun onGetPlaceIdSuccess(data: CurrentLocationModel) {
        _suggestions.value = HomeViewState.SuccessLocation(data)
    }
}