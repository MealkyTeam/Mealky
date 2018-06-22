package com.kuba.mealky.Presenters

import com.kuba.mealky.Database.Entities.MealData

interface ListOfMealsContract {


    interface View {
        fun loadData()
        fun onItemClick()
        fun onItemLongClick()
        fun clearData()
    }


    interface Presenter {
        fun getAllMeals(): List<MealData>
        fun changeViewToMeal()
        fun deleteMeal()
        fun refresh()
    }

}