package com.teammealky.mealky.domain.model

import com.google.gson.annotations.SerializedName

data class Page(
        @SerializedName("content") val meals: List<Meal>,
        val totalPages: Int,
        val totalElements: Int,
        val first: Boolean,
        val last: Boolean,
        val sorted: Boolean,
        val limit: Int
)