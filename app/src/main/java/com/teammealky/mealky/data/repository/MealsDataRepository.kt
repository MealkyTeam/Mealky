package com.teammealky.mealky.data.repository

import com.teammealky.mealky.data.repository.datasource.MealsRemoteSource
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.MealsRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsDataRepository @Inject constructor(private val remote: MealsRemoteSource) : MealsRepository {

    override fun getMeals(categoryId: Int, offset: Int, limit: Int): Single<List<Meal>>
            = remote.listMeals(categoryId, offset, limit)

    override fun getMealsByPage(page: Int, limit: Int): Single<Page>
            = remote.listMealsByPage(page, limit)
}