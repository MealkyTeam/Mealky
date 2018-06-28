package com.kuba.mealky.Database.Repositories

import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase


class MealsRepository(val mealkyDatabase: MealkyDatabase) {

    fun getAll(): List<MealData> {
        return mealkyDatabase?.mealDao()?.getAll()
    }

    fun insert(meal: MealData) {
        mealkyDatabase?.mealDao().insert(meal)
    }

    fun insertList(meals: List<MealData>) {
        for (meal in meals)
            insert(meal)
    }

}