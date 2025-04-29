package com.argel.weathertraker.core.exception

import androidx.annotation.StringRes
import com.argel.weathertraker.R
import java.lang.Exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
abstract class Failure {
    data class ServerError(val code: Int, val serverMessage: String?, val payload: String? = null) :
        Failure()

    object NetworkConnection : FeatureFailure(R.string.failure_no_internet)
    object DatabaseError : FeatureFailure(R.string.failure_db)
    object Unauthorized : FeatureFailure(R.string.failure_unauthorized)
    object Retry : FeatureFailure(R.string.failure_problem_retry)
    object InvalidCredentials : FeatureFailure(R.string.failure_invalid_credentials)

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(@StringRes val defaultMessage: Int = R.string.failure_no_internet) :
        Failure()
    object GenericNetworkConnection : FeatureFailure(R.string.failure_no_internet)
    class ErrorException(val exception: Exception) : Failure()
    object NotFound : Failure()
}

sealed class PagingFailure {
    data class Error(val failure: Failure) : Throwable("")
}