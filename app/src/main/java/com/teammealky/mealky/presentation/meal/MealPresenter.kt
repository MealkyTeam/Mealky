package com.teammealky.mealky.presentation.meal

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject


class MealPresenter @Inject constructor(
) : BasePresenter<MealPresenter.UI>() {

    var meal: Meal? = null

    private var checkedIngredients = mutableListOf<Ingredient>()

    fun onIngredientClicked(ingredient: Ingredient, isChecked: Boolean) {
        if (isChecked)
            checkedIngredients.add(ingredient)
        else
            checkedIngredients.remove(ingredient)
    }

    fun onIngredientsButtonClicked() {
        //todo save ingredients on device
        Timber.d("KUBA Method:onIngredientsButtonClicked ***** SAVE INGREDIENTS $checkedIngredients *****")
    }

    interface UI : BaseUI
}