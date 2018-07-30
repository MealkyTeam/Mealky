package com.kuba.mealky.Presenters

import com.kuba.mealky.Database.Entities.MealData

interface ListOfMealsContract {


    interface View {
        fun fillList(m: MutableList<MealData>)
        fun onItemClick()
        fun removeFromList(index: Int)
    }


    interface Presenter {
        fun loadMeals()
        fun changeViewToMeal()
        fun deleteMeal(meal: MealData, index: Int)
    }

}