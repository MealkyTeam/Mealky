package com.teammealky.mealky.domain.usecase.addmeal

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.repository.AddMealRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import java.io.File
import javax.inject.Inject

class AddMealUseCase @Inject constructor(
        private val repo: AddMealRepository
) : SingleUseCase<AddMealUseCase.Params, Boolean>() {

    override fun doWork(param: Params) =
            repo.addMeal(
                    Meal(-1,
                            param.name, param.prepTime,
                            param.preparation,
                            emptyList(),
                            true,
                            param.author,
                            emptyList(),
                            param.ingredients
                    ), param.images).toSingleDefault(true)

    data class Params(
            val name: String,
            val prepTime: Int,
            val preparation: String,
            val author: User,
            val ingredients: List<Ingredient>,
            val images: List<File>
    )
}
