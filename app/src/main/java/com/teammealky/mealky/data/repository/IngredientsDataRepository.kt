package com.teammealky.mealky.data.repository

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.IngredientsRepository
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientsDataRepository @Inject constructor(private val api: RestService) : IngredientsRepository {
    override fun listIngredients(query: String, limit: Int): Single<Page<Ingredient>>
            = api.clientShortTimeout().searchIngredients(query, limit)
}