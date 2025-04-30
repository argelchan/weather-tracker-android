package com.argel.weathertraker.domain.usecase

import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.functional.Either
import com.argel.weathertraker.core.functional.flatMap
import com.argel.weathertraker.core.interactor.UseCase
import com.argel.weathertraker.domain.repository.PlaceRepository
import com.argel.weathertraker.presentation.models.SuggestModel
import javax.inject.Inject

class GetAddressUC @Inject constructor(
    private val repository: PlaceRepository
): UseCase<List<SuggestModel>, String>() {
    override suspend fun run(params: String): Either<Failure, List<SuggestModel>> {
        return repository.searchPlaces(params).flatMap { list ->
            Either.Right(list.map { it.toModel() }) }
    }
}