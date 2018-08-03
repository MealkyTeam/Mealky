package com.kuba.mealky.database.repositories

import com.kuba.mealky.database.MealkyDatabase
import com.kuba.mealky.database.models.Meal
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


class MealsRepository(private val mealkyDatabase: MealkyDatabase) : Repository {

    init {
        compositeDisposable = CompositeDisposable()
        observable = Observable.just(
                .subscribeOn(Schedulers.io())
    }

    fun getAll(): MutableList<Meal> {
        return mealkyDatabase.mealDao().getAll()
    }

    fun insert(meal: Meal) {
        observable = Single.just(mealkyDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe { db -> db.mealDao().insert(meal) }

        observable.addTo(compositeDisposable)
        compositeDisposable.dispose()

    }

    fun insertList(meals: List<Meal>) {
        for (meal in meals)
            insert(meal)
    }

    fun delete(meal: Meal) {
        observable = Observable.just(mealkyDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe { db -> db.mealDao().delete(meal) }

        observable.addTo(compositeDisposable)
        compositeDisposable.dispose()
    }

    fun delete(index: Int) {

                observable.subscribe { db -> db.mealDao().delete(index) }

        observable.addTo(compositeDisposable)
        compositeDisposable.dispose()
    }

    fun findById(index: Int): Single<Meal> {
        val single=Single.just(mealkyDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe { db -> db.mealDao().findMealByiD(index) }
        return
    }

}