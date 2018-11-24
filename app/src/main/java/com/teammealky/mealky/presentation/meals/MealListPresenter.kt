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
        ui().perform { it.isLoading(true) }

        disposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(0, 0, 0),
                { page ->
                    ui().perform {
                        it.fillList(page.meals)
                        it.isLoading(false)
                    }
                },
                { e ->
                    Timber.e("FunName:loadMeals *****ERROR: $e *****")
                    loadMeals()
                }))
    }

    fun onItemClicked(model: Meal) {
        ui().perform { it.openItem(model) }
    }

    fun refresh() {
        ui().perform {
            it.isLoading(true)
        }

        disposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(0, 0, 0),
                { page ->
                    ui().perform {
                        it.refreshList(page.meals)
                        it.isLoading(false)
                    }
                },
                { e ->
                    Timber.e("FunName:refresh *****ERROR: $e *****")
                }))
    }

    interface UI : BaseUI {
        fun fillList(meals: List<Meal>)
        fun removeFromList(meal: Meal)
        fun openItem(meal: Meal)
        fun refreshList(meals: List<Meal>)
        fun isLoading(isLoading: Boolean)
    }
}