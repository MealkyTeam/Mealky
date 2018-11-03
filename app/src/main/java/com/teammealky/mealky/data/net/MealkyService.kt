package com.teammealky.mealky.data.net

import com.teammealky.mealky.domain.model.Meal
import io.reactivex.Single

interface MealkyService{

    fun getMeal(id:Int): Single<Meal>
}