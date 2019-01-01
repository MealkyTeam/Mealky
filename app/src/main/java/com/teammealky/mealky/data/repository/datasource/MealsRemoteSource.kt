package com.teammealky.mealky.data.repository.datasource

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRemoteSource @Inject constructor(private val api: RestService) {

    fun listMealsByPage(categoryId: Int,
                        offset: Int,
                        limit: Int): Single<Page<Meal>> = api.client().listMeals(categoryId,offset,limit)

    fun search(query: String,
               offset: Int,
               limit: Int): Single<Page<Meal>> = api.client().searchMeals(query,offset,limit)
}