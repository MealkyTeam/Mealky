package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.model.Unit
import io.reactivex.Single

interface UnitsRepository {
    fun listUnits(query: String,
                  limit: Int
    ): Single<Page<Unit>>
}