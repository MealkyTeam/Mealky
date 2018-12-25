package com.teammealky.mealky.presentation.shoppinglist.component.addingredient

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.domain.usecase.data.SearchIngredientsUseCase
import com.teammealky.mealky.domain.usecase.data.SearchUnitsUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class AddIngredientPresenter @Inject constructor(
        private val searchIngredientsUseCase: SearchIngredientsUseCase,
        private val searchUnitsUseCase: SearchUnitsUseCase
) : BasePresenter<AddIngredientPresenter.UI>() {

    var model: Ingredient = Ingredient.defaultIngredient()

    fun addIngredientBtnClicked() {
        ui().perform { it.addIngredient(model) }
    }

    fun fieldsChanged() {
        if (model.name.isBlank() || model.quantity == 0.0 || model.unit == Unit.defaultUnit())
            ui().perform { it.toggleAddButton(false) }
        else
            ui().perform { it.toggleAddButton(true) }
    }

    fun setupFinished() {
        disposable.add(searchIngredientsUseCase.execute(SearchIngredientsUseCase.Params("", LIMIT),
                { ingredients ->
                    searchUnits(ingredients)
                },
                { e ->
                    ui().perform { it.showErrorMessage({ setupFinished() }, e) }
                }
        ))
    }

    private fun searchUnits(ingredients: List<Ingredient>) {
        disposable.add(searchUnitsUseCase.execute(SearchUnitsUseCase.Params("", LIMIT),
                { units ->
                    ui().perform { it.setupAutocompleteAdapters(ingredients, units) }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ searchUnits(ingredients) }, e) }
                }
        ))
    }

    interface UI : BaseUI {
        fun toggleAddButton(isToggled: Boolean)
        fun addIngredient(ingredient: Ingredient)
        fun setupAutocompleteAdapters(ingredients: List<Ingredient>, units: List<Unit>)
    }

    companion object {
        const val LIMIT = -1
    }
}