package com.argel.weathertraker.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.argel.weathertraker.core.exception.Failure

abstract class BaseViewModel: ViewModel() {

    internal val _state: MutableLiveData<BaseViewState> = MutableLiveData()
    var state: LiveData<BaseViewState> = _state

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    var failure: LiveData<Failure> = _failure

    protected fun handleFailure(failure: Failure) {
        _failure.value = failure
        _state.value = BaseViewState.HideLoading
    }
}