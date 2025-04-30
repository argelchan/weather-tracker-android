package com.argel.weathertraker.core.interactor

import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import kotlinx.coroutines.*

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params>{

    abstract suspend fun run(params: Params): Either<Failure, Type>
    private val job = SupervisorJob()
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
    private val uiScope = CoroutineScope(dispatchers.main() + job)
    private val ioScope = CoroutineScope(dispatchers.io() + job)

    @DelicateCoroutinesApi
    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = ioScope.async { run(params) }
        uiScope.launch { onResult(job.await()) }
    }

    class None
    object Success
}