package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import io.reactivex.Single


interface MealsRepository{
    fun getMeals(categoryId: Int, offset: Int, limit: Int): Single<List<Meal>>
    fun getMealsByPage(page: Int, limit: Int): Single<Page>
}