package com.kuba.mealky.data.repository.datasource

import com.kuba.mealky.domain.model.Meal
import com.kuba.mealky.domain.service.FirebaseService
import io.reactivex.Single

class MealRemoteSource(private val api: FirebaseService){
    fun get(id: Int): Single<Meal> = api.client().getMeal(id)
}