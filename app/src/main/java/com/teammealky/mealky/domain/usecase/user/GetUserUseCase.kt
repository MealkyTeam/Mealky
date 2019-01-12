package com.teammealky.mealky.domain.usecase.user

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.repository.UserRepository
import com.teammealky.mealky.domain.usecase.ParamLessUseCase
import io.reactivex.Single
import javax.inject.Inject

 class GetUserUseCase @Inject constructor(
        private val repo: UserRepository
) : ParamLessUseCase<User>() {

    override fun doWork(): Single<User> = repo.getUser()
}