package com.teammealky.mealky.domain.repository


import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.model.User
import io.reactivex.Single

interface AuthorizationRepository {
    fun signInWithPassword(email: String,
                           password: String): Single<Token>

    fun signInWithToken(token: String): Single<User>
}