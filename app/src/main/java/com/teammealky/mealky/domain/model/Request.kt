package com.teammealky.mealky.domain.model


data class TokenRequest(val token: String) : LoginRequest

data class PasswordRequest(val email: String,
                           val password: String) : LoginRequest
interface LoginRequest
