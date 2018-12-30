package com.teammealky.mealky.data.repository

import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.domain.repository.UnitsRepository
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnitsDataRepository @Inject constructor(private val api: RestService) : UnitsRepository {
    override fun listUnits(query: String, limit: Int): Single<Page<Unit>> = api.clientShortTimeout().searchUnits(query, limit)
}