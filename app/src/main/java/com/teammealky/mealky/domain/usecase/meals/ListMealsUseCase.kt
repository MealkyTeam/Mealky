package com.teammealky.mealky.domain.usecase.meals

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

 class ListMealsUseCase @Inject constructor(
        private val repo: MealsRepository
) : SingleUseCase<ListMealsUseCase.Params, Page<Meal>>() {

    override fun doWork(param: Params): Single<Page<Meal>>{
        if(param.forceReload)
            repo.invalidate()

        return repo.searchMeals(param.query, param.page, param.limit)
    }

    data class Params(
            val query: String = "",
            val page: Int,
            val limit: Int,
            val forceReload: Boolean = false
    )
}