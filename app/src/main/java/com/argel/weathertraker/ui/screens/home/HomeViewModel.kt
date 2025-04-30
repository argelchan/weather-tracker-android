package com.argel.weathertraker.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.argel.weathertraker.core.presentation.BaseViewModel
import com.argel.weathertraker.domain.usecase.GetAddressUC
import com.argel.weathertraker.presentation.models.SuggestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAddressUc: GetAddressUC
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
}