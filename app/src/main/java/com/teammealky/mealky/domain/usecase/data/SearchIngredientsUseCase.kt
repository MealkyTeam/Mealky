package com.teammealky.mealky.domain.usecase.data

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.IngredientsRepository
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class SearchIngredientsUseCase @Inject constructor(
        private val repo: IngredientsRepository
) : SingleUseCase<SearchIngredientsUseCase.Params, List<Ingredient>>() {

    override fun doWork(param: Params): Single<List<Ingredient>> = repo.listIngredients(param.query, param.limit)

    data class Params(
            val query: String,
            val limit: Int
    )
}