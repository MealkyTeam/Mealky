package com.teammealky.mealky.domain.usecase.data

import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.domain.repository.UnitsRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class SearchUnitsUseCase @Inject constructor(
        private val repo: UnitsRepository
) : SingleUseCase<SearchUnitsUseCase.Params, Page<Unit>>() {

    override fun doWork(param: Params): Single<Page<Unit>> = repo.listUnits(param.query, param.limit)

    data class Params(
            val query: String,
            val limit: Int
    )
}