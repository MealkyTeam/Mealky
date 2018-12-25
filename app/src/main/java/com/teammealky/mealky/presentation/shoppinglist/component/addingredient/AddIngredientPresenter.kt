package com.teammealky.mealky.presentation.shoppinglist.component.addingredient

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class AddIngredientPresenter @Inject constructor() : BasePresenter<AddIngredientPresenter.UI>() {

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

    interface UI : BaseUI {
        fun toggleAddButton(isToggled: Boolean)
        fun addIngredient(ingredient: Ingredient)
    }
}