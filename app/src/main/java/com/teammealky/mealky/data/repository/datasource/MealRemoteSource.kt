package com.teammealky.mealky.data.repository.datasource

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.service.FirebaseService
import io.reactivex.Single

class MealRemoteSource(private val api: FirebaseService){
    fun get(id: Int): Single<Meal> = api.client().getMeal(id)
}