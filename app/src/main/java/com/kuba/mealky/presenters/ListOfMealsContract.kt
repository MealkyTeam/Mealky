package com.kuba.mealky.presenters

import com.kuba.mealky.database.models.Meal

interface ListOfMealsContract {

    interface View {
        fun fillList(m: MutableList<Meal>)
        fun onItemClick()
        fun removeFromList(index: Int)
    }


    interface Presenter {
        fun loadMeals()
        fun changeViewToMeal()
        fun deleteMeal(meal: Meal, index: Int)
    }

}