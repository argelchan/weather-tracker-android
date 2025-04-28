package com.argel.weathertraker.core.presentation

import androidx.lifecycle.ViewModel
import com.argel.weathertraker.core.exception.Failure
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel: ViewModel() {
    var state: MutableStateFlow<BaseViewState?> = MutableStateFlow(null)
    var failure: MutableStateFlow<Failure?> = MutableStateFlow(null)

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
        state.value = BaseViewState.HideLoading
    }
}