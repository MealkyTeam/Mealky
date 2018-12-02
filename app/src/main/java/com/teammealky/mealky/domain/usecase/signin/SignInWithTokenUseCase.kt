package com.teammealky.mealky.domain.usecase.signin

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class SignInWithTokenUseCase @Inject constructor(
        private val repo: AuthorizationRepository
) : SingleUseCase<SignInWithTokenUseCase.Params, User>() {

    override fun doWork(param: Params): Single<User> {
        return repo.signInWithToken(param.token)
    }

    data class Params(
            val token: String
    )
}