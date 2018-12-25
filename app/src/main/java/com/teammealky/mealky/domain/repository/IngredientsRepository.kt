package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Ingredient
import io.reactivex.Single

interface IngredientsRepository {
    fun listIngredients(query: String,
                        limit: Int
    ): Single<List<Ingredient>>
}