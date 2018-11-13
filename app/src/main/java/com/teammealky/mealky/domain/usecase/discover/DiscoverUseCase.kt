package com.teammealky.mealky.domain.usecase.discover

import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class DiscoverUseCase @Inject constructor(
        private val repo: MealsRepository
) : SingleUseCase<DiscoverUseCase.Params, Page>() {


    override fun doWork(param: Params): Single<Page> = repo.getMealsByPage(param.page, param.limit)

    data class Params(
            val page: Int,
            val limit: Int
    )
}