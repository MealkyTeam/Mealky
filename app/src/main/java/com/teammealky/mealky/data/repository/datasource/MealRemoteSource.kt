package com.teammealky.mealky.data.repository.datasource

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRemoteSource @Inject constructor(private val api: RestService){
    fun get(id: Int): Single<Meal> = api.client().getMeal(id)
}