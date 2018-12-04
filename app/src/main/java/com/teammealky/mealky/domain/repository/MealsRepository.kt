package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Page
import io.reactivex.Single


interface MealsRepository{
    fun getMealsByPage(categoryId: Int, page: Int, limit: Int): Single<Page>
}