package com.teammealky.mealky.domain.usecase.signin

import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

open class SignInWithTokenUseCase @Inject constructor(
        private val repo: AuthorizationRepository
) : SingleUseCase<SignInWithTokenUseCase.Params, Response<Void>>() {

    override fun doWork(param: Params): Single<Response<Void>> {
        return repo.signInWithToken(param.token)
    }

    data class Params(
            val token: String
    )
}