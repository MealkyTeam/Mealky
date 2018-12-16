package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Ingredient
import io.reactivex.Completable
import io.reactivex.Single


interface ShoppingListRepository {
    fun list(): Single<List<Ingredient>>
    fun add(ingredients: List<Ingredient>): Completable
    fun remove(id: Int): Completable
    fun clear(): Completable
}