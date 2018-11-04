package com.teammealky.mealky.domain.usecase.meals

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class ListMealsUseCase @Inject constructor(
        private val repo: MealsRepository
) : SingleUseCase<ListMealsUseCase.Params, List<Meal>>() {

    override fun doWork(param: Params): Single<List<Meal>> = repo.getMeals(param.categoryId, param.offset, param.limit)

    data class Params(
            val categoryId: Int,
            val offset: Int,
            val limit: Int
    )
}