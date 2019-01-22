package com.teammealky.mealky.domain.usecase.signup

import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
        private val repo: AuthorizationRepository
) : SingleUseCase<SignUpUseCase.Params, Boolean>() {

    override fun doWork(param: Params): Single<Boolean> {
        return repo.signUp(param.username, param.email, param.password).toSingleDefault(true)
    }

    data class Params(
            val username: String,
            val email: String,
            val password: String
    )
}