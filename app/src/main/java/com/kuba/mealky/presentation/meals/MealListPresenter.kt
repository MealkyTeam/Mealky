package com.kuba.mealky.presentation.meals

import com.kuba.mealky.domain.model.Meal
import com.kuba.mealky.presentation.commons.presenter.BasePresenter
import com.kuba.mealky.presentation.commons.presenter.BaseUI


class MealListPresenter(
//TODO add usecase
//        private val getMealsUseCase;

) : BasePresenter<MealListPresenter.UI>() {

    private var meals: MutableList<Meal> = mutableListOf()

    fun loadMeals() {
        //Mocked meals, remove
        meals = mutableListOf(
                Meal(1, "test1", 0, "testPrep1", listOf("https://cdn.pixabay.com/photo/2014/06/11/17/00/cook-366875__480.jpg")),
                Meal(2, "test2", 0, "testPrep2", listOf("https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032__480.jpg")),
                Meal(3, "test3", 0, "testPrep3", listOf("https://cdn.pixabay.com/photo/2017/06/06/22/46/mediterranean-cuisine-2378758__480.jpg"))
        )
        ui().perform { it.fillList(meals) }
    }

    fun changeViewToMeal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteMeal(meal: Meal, index: Int) {

    }

    interface UI : BaseUI {
        fun fillList(meals: MutableList<Meal>)
        fun removeFromList(meal: Meal)
        fun goToMeal(meal: Meal)
    }
}