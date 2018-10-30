package com.kuba.mealky.domain.repository


import com.kuba.mealky.domain.model.Meal
import io.reactivex.Single

interface MealRepository{
    fun get(id: Int): Single<Meal>
}