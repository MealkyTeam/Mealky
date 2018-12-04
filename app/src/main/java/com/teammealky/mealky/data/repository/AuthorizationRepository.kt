package com.teammealky.mealky.data.repository

import com.teammealky.mealky.domain.model.PasswordSignInRequest
import com.teammealky.mealky.domain.model.SignUpRequest
import com.teammealky.mealky.domain.model.TokenSignInRequest
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationDataRepository @Inject constructor(private val api: RestService) : AuthorizationRepository {
    override fun signUp(username: String, email: String, password: String): Single<Response<Void>> = api.client().signUp(SignUpRequest(username, email, password))

    override fun signInWithPassword(email: String, password: String): Single<User> =
            api.client().signInWithPassword(PasswordSignInRequest(email, password))

    override fun signInWithToken(token: String) =
            api.client().signInWithToken(TokenSignInRequest(token))
}