package com.teammealky.mealky.domain.model

import java.lang.Exception

data class ErrorResponse(val httpCode: Int, val errors: List<ErrorResponse.Error>) : Exception() {

    val summary: String
        get() {
            val messages = ArrayList<String>()
            errors.forEach {
                if (it.title.isNotEmpty()) messages.add(it.title)
                if (it.detail.isNotEmpty()) messages.add(it.detail)
            }
            return messages.joinToString(" ")
        }

    val isForbidden: Boolean
        get() = httpCode == 403

    val isConflict: Boolean
        get() = httpCode == 409

    val isBadRequest: Boolean
        get() = httpCode == 400

    val isUnavailable: Boolean
        get() = httpCode == 503

    val title: String
        get() = errors.getOrNull(0)?.title ?: ""

    val details: String
        get() = errors.getOrNull(0)?.detail?: ""

    data class Error(
            val status: String,
            val title: String,
            val detail: String,
            val meta: HashMap<String, String>? = null
    )
}