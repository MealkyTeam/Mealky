package com.kuba.mealky.Presenters

import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase

class ListOfMealsPresenter(val mealkyDatabase: MealkyDatabase) : BasePresenter<ListOfMealsContract.View>(), ListOfMealsContract.Presenter {

    override fun getAllMeals(): List<MealData> {
        val meals =
                mealkyDatabase?.mealDao()?.getAll()
        /*val meals: List<MealData> = listOf(
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(4, "meal4", 34, "prep4")
        )*/
        return meals
    }

    override fun changeViewToMeal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteMeal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}