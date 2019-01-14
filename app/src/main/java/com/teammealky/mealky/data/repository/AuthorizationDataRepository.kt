package com.teammealky.mealky.data.repository

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.model.request.PasswordSignInRequest
import com.teammealky.mealky.domain.model.request.SignUpRequest
import com.teammealky.mealky.domain.model.request.TokenSignInRequest
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationDataRepository @Inject constructor(private val api: RestService) : AuthorizationRepository {
    override fun signUp(username: String, email: String, password: String): Completable =
            api.client().signUp(SignUpRequest(username, email, password))


    override fun signInWithPassword(email: String, password: String): Single<User> =
            api.client().signInWithPassword(PasswordSignInRequest(email, password))

    override fun signInWithToken(token: String) =
            api.client().signInWithToken(TokenSignInRequest(token))

    override fun resetPassword(email: String) =
            api.client().resetPassword(email)
}