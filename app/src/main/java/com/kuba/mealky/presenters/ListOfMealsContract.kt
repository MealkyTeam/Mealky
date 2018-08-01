package com.kuba.mealky.presenters

import com.kuba.mealky.database.models.Meal

interface ListOfMealsContract {

    interface View {
        fun fillList(listOfMeals: MutableList<Meal>)
        fun onItemClick(meal:Meal)
        fun removeFromList(index: Int)
    }


    interface Presenter {
        fun loadMeals()
        fun changeViewToMeal(meal:Meal)
        fun deleteMeal(meal: Meal, index: Int)
    }

}