package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Meal
import io.reactivex.Completable
import java.io.File

interface AddMealRepository {
    fun addMeal(meal: Meal, images: List<File>): Completable
}