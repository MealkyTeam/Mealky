package com.teammealky.mealky.domain.service

import com.teammealky.mealky.data.net.service.MealkyService

interface RestService{
    fun client(): MealkyService
    fun clientShortTimeout(): MealkyService
}