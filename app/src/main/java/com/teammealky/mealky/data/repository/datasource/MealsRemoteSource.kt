package com.teammealky.mealky.data.repository.datasource

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRemoteSource @Inject constructor(private val api: RestService) : MealsRepository {

    override fun searchMeals(query: String,
                             page: Int,
                             limit: Int): Single<Page<Meal>> = api.client().searchMeals(query, page, limit)

    override fun invalidate() {
        Timber.tag("KUBA").v("invalidate NOT IMPLEMENTED")
    }
}