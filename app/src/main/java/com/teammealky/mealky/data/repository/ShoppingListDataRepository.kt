package com.teammealky.mealky.data.repository

import com.teammealky.mealky.data.repository.datasource.ShoppingListLocalSource
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class ShoppingListDataRepository @Inject constructor(
        private val local: ShoppingListLocalSource
) : ShoppingListRepository {

    override fun list(): Single<List<Ingredient>> = local.list()

    override fun add(ingredients: List<Ingredient>): Completable = local.add(ingredients)

    override fun remove(ingredient: Ingredient): Completable = local.remove(ingredient)

    override fun clear(): Completable = local.clear()
}