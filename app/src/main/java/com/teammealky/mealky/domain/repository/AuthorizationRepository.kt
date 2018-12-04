package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.User
import io.reactivex.Single
import retrofit2.Response

interface AuthorizationRepository {
    fun signInWithPassword(email: String,
                           password: String): Single<User>

    fun signInWithToken(token: String): Single<User>

    fun signUp(username: String,
               email: String,
               password: String): Single<Response<Void>>
}