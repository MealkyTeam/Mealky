package com.teammealky.mealky.domain.usecase.resetpassword

import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
        private val repo: AuthorizationRepository
) : SingleUseCase<String, Boolean>() {

    override fun doWork(param: String): Single<Boolean> {
        return repo.resetPassword(param).toSingleDefault(true)
    }
}