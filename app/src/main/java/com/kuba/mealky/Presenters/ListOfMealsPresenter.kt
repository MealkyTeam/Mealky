package com.kuba.mealky.Presenters

import android.os.AsyncTask
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.Toast
import com.kuba.mealky.Database.DBWorkerThread
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase
import android.os.Looper



class ListOfMealsPresenter(val mealkyDatabase: MealkyDatabase) : BasePresenter<ListOfMealsContract.View>(), ListOfMealsContract.Presenter {

    override fun loadMeals() {
        var meals: List<MealData> = emptyList()
        val task = Runnable {
            meals = mealkyDatabase?.mealDao()?.getAll()
            Log.e("MealsSizeInTask", meals.size.toString())
            view?.fillList(meals)
        }
        AsyncTask.execute(task)
        Log.e("MealsSizeAfterTask", meals.size.toString())
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