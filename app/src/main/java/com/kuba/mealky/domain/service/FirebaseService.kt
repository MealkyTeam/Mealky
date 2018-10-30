package com.kuba.mealky.domain.service

import com.kuba.mealky.data.net.MealkyService

interface FirebaseService{
    fun client(): MealkyService
}