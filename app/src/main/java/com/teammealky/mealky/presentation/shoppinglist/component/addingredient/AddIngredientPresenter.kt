package com.teammealky.mealky.presentation.shoppinglist.component.addingredient

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.domain.usecase.data.SearchIngredientsUseCase
import com.teammealky.mealky.domain.usecase.data.SearchUnitsUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class AddIngredientPresenter @Inject constructor(
        private val searchIngredientsUseCase: SearchIngredientsUseCase,
        private val searchUnitsUseCase: SearchUnitsUseCase
) : BasePresenter<AddIngredientPresenter.UI>() {

    var model: Ingredient = Ingredient.defaultIngredient()

    fun setupFinished() {
        ui().perform { it.isLoading(true) }
        disposable.add(searchIngredientsUseCase.execute(SearchIngredientsUseCase.Params("", LIMIT),
                { page ->
                    searchUnits(page.items)
                },
                { e ->
                    Timber.d("KUBA_LOG Method:setupFinished ***** $e *****")
                    searchUnits(emptyList())
                }
        ))
    }

    private fun searchUnits(ingredients: List<Ingredient>) {
        disposable.add(searchUnitsUseCase.execute(SearchUnitsUseCase.Params("", LIMIT),
                { page ->
                    ui().perform {
                        it.setupAutocompleteAdapters(ingredients, page.items)
                        it.isLoading(false)
                    }
                },
                { e ->
                    Timber.d("KUBA_LOG Method:searchUnits ***** $e *****")
                    ui().perform {
                        it.setupAutocompleteAdapters(ingredients, emptyList())
                        it.isLoading(false)
                    }
                }
        ))
    }

    fun addIngredientBtnClicked() {
        ui().perform { it.addIngredient(model) }
    }

    fun fieldsChanged() {
        if (model.name.isBlank() || model.quantity == 0.0 || model.unit == Unit.defaultUnit())
            ui().perform { it.toggleAddButton(false) }
        else
            ui().perform { it.toggleAddButton(true) }
    }

    interface UI : BaseUI {
        fun toggleAddButton(isToggled: Boolean)
        fun addIngredient(ingredient: Ingredient)
        fun setupAutocompleteAdapters(ingredients: List<Ingredient>, units: List<Unit>)
        fun isLoading(isLoading: Boolean)
    }

    companion object {
        const val LIMIT = -1
    }
}