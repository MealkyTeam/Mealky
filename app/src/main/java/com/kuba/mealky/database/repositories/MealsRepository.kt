package com.kuba.mealky.database.repositories

import com.kuba.mealky.database.MealkyDatabase
import com.kuba.mealky.database.models.Meal


class MealsRepository(val mealkyDatabase: MealkyDatabase) {

    fun getAll(): MutableList<Meal> {
        return mealkyDatabase.mealDao().getAll()
    }

    fun insert(meal: Meal) {
        mealkyDatabase.mealDao().insert(meal)
    }

    fun insertList(meals: List<Meal>) {
        for (meal in meals)
            insert(meal)
    }

    fun delete(meal: Meal) {
        mealkyDatabase.mealDao().delete(meal)
    }

    fun delete(index: Int) {
        val meal = findById(index)
        if (meal != null)
            mealkyDatabase.mealDao().delete(meal)
    }

    fun findById(index: Int): Meal? {
        return mealkyDatabase.mealDao().findMealByiD(index)
    }

}