package com.teammealky.mealky.domain.model


data class TokenRequest(val token: String)
//todo maybe change username to password
data class PasswordRequest(val username: String,
                           val password: String)
