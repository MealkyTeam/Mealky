package com.teammealky.mealky.domain.model

data class TokenSignInRequest(val token: String)

data class PasswordSignInRequest(val email: String,
                                 val password: String)

data class SignUpRequest(val username: String,
                         val email: String,
                         val password: String)
