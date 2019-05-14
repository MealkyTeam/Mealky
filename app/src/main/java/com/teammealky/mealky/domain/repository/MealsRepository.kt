package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import io.reactivex.Single


interface MealsRepository{
    fun searchMeals(query: String, page: Int, limit: Int): Single<Page<Meal>>
    fun invalidate()
}