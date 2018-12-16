package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface AuthorizationRepository {
    fun signInWithPassword(email: String,
                           password: String): Single<User>

    fun signInWithToken(token: String): Single<User>

    fun signUp(username: String,
               email: String,
               password: String): Completable
}