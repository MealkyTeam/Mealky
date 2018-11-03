package com.teammealky.mealky.domain.service

import com.teammealky.mealky.data.net.MealkyService

interface FirebaseService{
    fun client(): MealkyService
}