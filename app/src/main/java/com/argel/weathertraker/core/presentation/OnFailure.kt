package com.soriana.sorianadelivery.core.presentation

import com.argel.weathertraker.core.exception.Failure

interface OnFailure {

    fun handleFailure(failure: Failure?)

}