package com.teammealky.mealky.domain.model


data class TokenRequest(val token: String)

data class PasswordRequest(val email: String,
                           val password: String)
