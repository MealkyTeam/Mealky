package com.teammealky.mealky.domain.usecase.shoppinglist

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class AddToShoppingListUseCase @Inject constructor(
        private val repo: ShoppingListRepository
) : SingleUseCase<List<Ingredient>, Boolean>() {

    override fun doWork(param: List<Ingredient>): Single<Boolean> {
        return repo.add(param).toSingleDefault(true)
    }
}