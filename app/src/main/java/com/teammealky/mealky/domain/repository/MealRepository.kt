package com.teammealky.mealky.domain.repository


import com.teammealky.mealky.domain.model.Meal
import io.reactivex.Single

interface MealRepository{
    fun get(id: Int): Single<Meal>
}