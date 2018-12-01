package com.teammealky.mealky.data.repository

import com.teammealky.mealky.domain.model.PasswordRequest
import com.teammealky.mealky.domain.model.TokenRequest
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationDataRepository @Inject constructor(private val api: RestService) : AuthorizationRepository {

    override fun signInWithPassword(email: String, password: String): Single<String> =
            api.client().signInWithPassword(PasswordRequest(email, password))

    override fun signInWithToken(token: String) =
            api.client().signInWithToken(TokenRequest(token))
}