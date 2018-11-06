package com.teammealky.mealky.presentation.discover

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class DiscoverPresenter @Inject constructor(private val getMealsUseCase: ListMealsUseCase
) : BasePresenter<DiscoverPresenter.UI>() {
    private var currentMealId = 0
    private var meals = emptyList<Meal>()
    fun likeClicked() {
        ui().perform { it.openItem(meals[currentMealId]) }
    }

    fun dislikeClicked() {
        currentMealId++
        if (meals.size > currentMealId)
            ui().perform { it.setMeal(meals[currentMealId]) }
        else
            setup()
    }

    fun swipedLeftToRight() {

    }

    fun swipedRightToLeft() {

    }

    fun setup() {
        currentMealId = 0
        disposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(0, 0, 0),
                { list ->
                    meals = list
                    ui().perform {
                        it.setMeal(meals[currentMealId])
                    }
                },
                { e ->
                    Timber.e("FunName:loadMeals *****ERROR: $e *****")
                }))
    }

    interface UI : BaseUI {
        fun openItem(meal: Meal)
        fun setMeal(meal: Meal)
    }
}