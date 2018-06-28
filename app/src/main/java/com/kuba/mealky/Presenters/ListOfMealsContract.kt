package com.kuba.mealky.Presenters

import android.arch.lifecycle.LiveData
import com.kuba.mealky.Database.Entities.MealData

interface ListOfMealsContract {


    interface View {
        fun fillList(m: List<MealData>)
        fun onItemClick()
        fun onItemLongClick()
    }


    interface Presenter {
        fun loadMeals()
        fun changeViewToMeal()
        fun deleteMeal()
    }

}