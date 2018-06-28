package com.kuba.mealky.Database.Repositories

import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase


class MealsRepository(val mealkyDatabase: MealkyDatabase) {

    fun getAll(): MutableList<MealData> {
        return mealkyDatabase.mealDao().getAll()
    }

    fun insert(meal: MealData) {
        mealkyDatabase.mealDao().insert(meal)
    }

    fun insertList(meals: List<MealData>) {
        for (meal in meals)
            insert(meal)
    }

    fun delete(meal: MealData) {
        mealkyDatabase.mealDao().delete(meal)
    }

    fun delete(index: Int) {
        val meal = findById(index)
        if (meal != null)
            mealkyDatabase.mealDao().delete(meal)
    }

    fun findById(index: Int): MealData? {
        return mealkyDatabase.mealDao().findMealByiD(index)
    }

}