package com.teammealky.mealky.data.repository

import com.teammealky.mealky.data.repository.datasource.MealsLocalSource
import com.teammealky.mealky.data.repository.datasource.MealsRemoteSource
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.MealsRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsDataRepository @Inject constructor(
        private val remote: MealsRemoteSource,
        private val local: MealsLocalSource
) : MealsRepository {

    override fun searchMeals(query: String, page: Int, limit: Int): Single<Page<Meal>> {
        return Single.concat(
                local.searchMeals(query, page, limit),
                remote.searchMeals(query, page, limit).doOnSuccess { item ->
                    local.setMeals(query, item.copy(pageNumber = page, limit = limit))
                }
        ).filter { it !== local.emptyItem }.first(Page.emptyPage())
    }
}