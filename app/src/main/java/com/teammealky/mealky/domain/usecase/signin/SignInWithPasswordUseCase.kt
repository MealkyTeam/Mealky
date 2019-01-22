package com.teammealky.mealky.domain.usecase.signin

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

 class SignInWithPasswordUseCase @Inject constructor(
        private val repo: AuthorizationRepository
) : SingleUseCase<SignInWithPasswordUseCase.Params, User>() {

    override fun doWork(param: Params): Single<User> {
        return repo.signInWithPassword(param.email, param.password)
    }

    data class Params(
            val email: String,
            val password: String
    )
}