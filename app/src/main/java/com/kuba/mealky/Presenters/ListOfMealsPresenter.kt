package com.kuba.mealky.Presenters

import android.util.Log
import com.kuba.mealky.Activities.ListOfMealsActivity
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.Repositories.MealsRepository


class ListOfMealsPresenter(val repository: MealsRepository) : BasePresenter<ListOfMealsContract.View>(), ListOfMealsContract.Presenter {
    private var meals: MutableList<MealData> = mutableListOf()
    override fun loadMeals() {
        val task = Runnable {
            meals = repository.getAll()
        }
        val thread = Thread(task)
        thread.start()
        thread.join()

        view?.fillList(meals)
    }

    override fun changeViewToMeal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteMeal(meal: MealData, index: Int) {
        val task = Runnable {
            repository.delete(meal)
            meals = repository.getAll()
        }
        val thread = Thread(task)
        thread.start()
        thread.join()
        Log.e(ListOfMealsActivity.TAG, "DELETE MEAL TEST:" + meals)
        view?.removeFromList(index)
    }
}