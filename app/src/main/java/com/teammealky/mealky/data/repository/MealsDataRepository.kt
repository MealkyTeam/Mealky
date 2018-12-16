package com.teammealky.mealky.data.repository

import com.teammealky.mealky.data.repository.datasource.MealsRemoteSource
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.MealsRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsDataRepository @Inject constructor(private val remote: MealsRemoteSource) : MealsRepository {

    override fun getMealsByPage(categoryId: Int, page: Int, limit: Int): Single<Page> = remote.listMealsByPage(categoryId, page, limit)
}