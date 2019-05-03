package com.teammealky.mealky.domain.model

import com.google.gson.annotations.SerializedName

data class Page<T>(
        @SerializedName("content") val items: List<T>,
        val totalPages: Int,
        val totalElements: Int,
        val first: Boolean,
        val last: Boolean,
        val sorted: Boolean,
        val limit: Int,
        @SerializedName("number") val pageNumber: Int
) {
    companion object {
        fun <T> emptyPage() =
                Page(listOf<T>(), -1, -1, true, true, true, -1, -1)
    }
}