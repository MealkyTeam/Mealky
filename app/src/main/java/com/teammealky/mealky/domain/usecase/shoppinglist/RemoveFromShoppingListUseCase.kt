package com.teammealky.mealky.domain.usecase.shoppinglist

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

 class RemoveFromShoppingListUseCase @Inject constructor(
        private val repo: ShoppingListRepository
) : SingleUseCase<Ingredient, Boolean>() {

    override fun doWork(param: Ingredient): Single<Boolean> {
        return repo.remove(param).toSingleDefault(true)
    }
}
