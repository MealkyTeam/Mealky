package com.teammealky.mealky.presentation.meals

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject


class MealListPresenter @Inject constructor(
        private val getMealsUseCase: ListMealsUseCase
) : BasePresenter<MealListPresenter.UI>() {

    fun loadMeals() {
        disposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(0, 0, 0),
                { list ->
                    Timber.e("FunName:loadMeals *****$list *****")
                    ui().perform { it.fillList(list) }
                },
                { e ->
                    Timber.e("FunName:loadMeals *****ERROR: $e *****")
                }))

    }

    fun onItemClicked(model: Meal) {
        ui().perform { it.openItem(model) }
    }

    interface UI : BaseUI {
        fun fillList(meals: List<Meal>)
        fun removeFromList(meal: Meal)
        fun openItem(meal: Meal)
    }
}