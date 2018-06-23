package com.kuba.mealky.Presenters

import com.kuba.mealky.Database.Entities.MealData

interface ListOfMealsContract {


    interface View {
        fun fillList(m: List<MealData>)
        fun onItemClick()
        fun onItemLongClick()
        fun clearData()
    }


    interface Presenter {
        fun loadMeals()
        fun changeViewToMeal()
        fun deleteMeal()
        fun refresh()
    }

}