package com.teammealky.mealky.domain.usecase.shoppinglist

import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.usecase.ParamLessUseCase
import io.reactivex.Single
import javax.inject.Inject

open class ClearShoppingListUseCase @Inject constructor(
        private val repo: ShoppingListRepository
) : ParamLessUseCase<Boolean>() {

    override fun doWork(): Single<Boolean> = repo.clear().toSingleDefault(true)
}