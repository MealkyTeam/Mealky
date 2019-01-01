package com.teammealky.mealky.domain.usecase.user

import com.teammealky.mealky.domain.repository.UserRepository
import com.teammealky.mealky.domain.usecase.ParamLessUseCase
import io.reactivex.Single
import javax.inject.Inject

open class ClearUserUseCase @Inject constructor(
        private val repo: UserRepository
) : ParamLessUseCase<Boolean>() {

    override fun doWork(): Single<Boolean> = repo.clearUser()
}