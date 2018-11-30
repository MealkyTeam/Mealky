package com.teammealky.mealky.domain.usecase.signin

import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

open class SignInUseCase @Inject constructor(
        private val repo: AuthorizationRepository
) : SingleUseCase<SignInUseCase.Params, Response<Void>>() {

    override fun doWork(param: Params): Single<Response<Void>> {
        Timber.d("KUBA Method:doWork *****  *****")
//        return if(!param.token.isNullOrBlank())
            return repo.signInWithToken(param.token.orEmpty())
//        else
//            repo.signInWithPassword(param.email.orEmpty(),param.password.orEmpty())
    }

    data class Params(
            val token: String? = null,
            val email: String? = null,
            val password: String? = null
    )
}