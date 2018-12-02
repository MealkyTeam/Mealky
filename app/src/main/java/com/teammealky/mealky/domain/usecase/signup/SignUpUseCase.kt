package com.teammealky.mealky.domain.usecase.signup

import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

open class SignUpUseCase @Inject constructor(
        private val repo: AuthorizationRepository
) : SingleUseCase<SignUpUseCase.Params, Response<Void>>() {

    override fun doWork(param: Params): Single<Response<Void>> {
        return repo.signUp(param.username, param.email, param.password)
    }

    data class Params(
            val username: String,
            val email: String,
            val password: String
    )
}