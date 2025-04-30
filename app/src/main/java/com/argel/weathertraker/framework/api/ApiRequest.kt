package com.argel.weathertraker.framework.api

import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.core.platform.NetworkHandler
import retrofit2.Call

interface ApiRequest {

    fun <T, R> makeRequest(
        networkHandler: NetworkHandler,
        call: Call<T>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return when (networkHandler.isNetworkConnected) {
            true -> {
                try {
                    val response = call.execute()
                    when (response.isSuccessful) {
                        true -> Either.Right(transform(response.body() ?: default))
                        false -> Either.Left(Failure.ServerError(500, ""))
                    }
                } catch (e: Exception) {
                    Either.Left(Failure.ServerError(500, e.message))
                }
            }
            false -> Either.Left(Failure.NetworkConnection)
        }
    }

    suspend fun <T, R> makeRequestSuspend(
        networkHandler: NetworkHandler,
        call: suspend () -> T,
        transform: suspend (T) -> R,
        default: T
    ): Either<Failure, R> {
        return when (networkHandler.isNetworkConnected) {
            true -> {
                try {
                    val response = call()
                    Either.Right(transform(response ?: default))
                } catch (e: Exception) {
                    Either.Left(Failure.ServerError(500, e.message))
                }
            }
            false -> Either.Left(Failure.NetworkConnection)
        }
    }

}