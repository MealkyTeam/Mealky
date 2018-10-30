package com.kuba.mealky.data.net

import com.kuba.mealky.domain.model.Meal
import io.reactivex.Single

interface MealkyService{

    fun getMeal(id:Int): Single<Meal>
}