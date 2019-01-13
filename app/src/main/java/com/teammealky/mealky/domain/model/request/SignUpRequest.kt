package com.teammealky.mealky.domain.model.request

data class SignUpRequest(val username: String,
                         val email: String,
                         val password: String)