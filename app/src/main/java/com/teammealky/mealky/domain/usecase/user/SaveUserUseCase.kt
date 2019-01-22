package com.teammealky.mealky.domain.usecase.user

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.repository.UserRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import javax.inject.Inject

 class SaveUserUseCase @Inject constructor(
        private val repo: UserRepository
) : SingleUseCase<User, Boolean>() {

    override fun doWork(param: User) = repo.setUser(param)
}