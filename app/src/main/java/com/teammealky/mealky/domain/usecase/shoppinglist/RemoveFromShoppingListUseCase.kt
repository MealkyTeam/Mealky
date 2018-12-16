package com.teammealky.mealky.domain.usecase.shoppinglist

import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class RemoveFromShoppingListUseCase @Inject constructor(
        private val repo: ShoppingListRepository
) : SingleUseCase<Int, Boolean>() {

    override fun doWork(param: Int): Single<Boolean> {
        return repo.remove(param).toSingleDefault(true)
    }
}
