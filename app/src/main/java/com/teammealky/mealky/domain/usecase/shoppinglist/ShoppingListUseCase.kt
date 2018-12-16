package com.teammealky.mealky.domain.usecase.shoppinglist

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.usecase.ParamLessUseCase
import io.reactivex.Single
import javax.inject.Inject

open class ShoppingListUseCase @Inject constructor(
        private val repo: ShoppingListRepository
) : ParamLessUseCase<List<Ingredient>>() {

    override fun doWork(): Single<List<Ingredient>> = repo.list()

}