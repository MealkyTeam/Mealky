package com.teammealky.mealky.domain.repository


import io.reactivex.Single

import retrofit2.Response

interface AuthorizationRepository {
    fun signInWithPassword(email: String,
                           password: String): Single<String>

    fun signInWithToken(token: String): Single<Response<Void>>
}