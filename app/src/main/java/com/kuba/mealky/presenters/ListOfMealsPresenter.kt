package com.kuba.mealky.presenters

import com.kuba.mealky.database.models.Meal
import com.kuba.mealky.database.repositories.MealsRepository


class ListOfMealsPresenter(val repository: MealsRepository) : BasePresenter<ListOfMealsContract.View>(), ListOfMealsContract.Presenter {
    private var meals: MutableList<Meal> = mutableListOf()
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

    override fun deleteMeal(meal: Meal, index: Int) {
        val task = Runnable {
            repository.delete(meal)
            meals = repository.getAll()
        }
        val thread = Thread(task)
        thread.start()
        thread.join()
        view?.removeFromList(index)
    }
}