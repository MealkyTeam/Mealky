package com.teammealky.mealky.presentation.meal

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.usecase.shoppinglist.AddToShoppingListUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import javax.inject.Inject

class MealPresenter @Inject constructor(
        private val addToShoppingListUseCase: AddToShoppingListUseCase
) : BasePresenter<MealPresenter.UI>() {

    private var meal: Meal = Meal.basicMeal()
    private var ingredientModels = emptyList<IngredientViewModel>()

    fun onCreated(meal: Meal?) {
        if (this.meal == Meal.basicMeal()) {
            this.meal = meal ?: Meal.basicMeal()
            ingredientModels = meal?.ingredients?.map { IngredientViewModel(it, false) }
                    ?: emptyList()
        }
    }

    override fun attach(ui: UI) {
        super.attach(ui)

        ui().perform {
            it.setupView(meal, ingredientModels)
            it.enableButton(ingredientModels.any { meal -> meal.isChecked })
        }
    }

    fun onIngredientClicked(model: IngredientViewModel) {
        ingredientModels = ingredientModels.map {
            return@map if (it.item == model.item)
                it.copy(isChecked = !it.isChecked)
            else
                it
        }
        ui().perform { ui -> ui.enableButton(ingredientModels.any { it.isChecked }) }
    }

    fun onIngredientsButtonClicked() {
        val checkedIngredients = getAllCheckedIngredients()
        disposable.add(addToShoppingListUseCase.execute(
                checkedIngredients,
                { succeeded ->
                    ui().perform { it.showToast(succeeded) }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ onIngredientsButtonClicked() }, e) }
                })
        )
    }

    private fun getAllCheckedIngredients(): List<Ingredient> {
        return ingredientModels
                .filter { it.isChecked }
                .map { it.item }
    }

    interface UI : BaseUI {
        fun showToast(succeeded: Boolean)
        fun setupView(meal: Meal, ingredientsViewModels: List<IngredientViewModel>)
        fun enableButton(isEnabled: Boolean)
    }
}