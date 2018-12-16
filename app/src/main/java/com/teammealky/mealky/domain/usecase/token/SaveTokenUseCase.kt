package com.teammealky.mealky.domain.usecase.token

import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.repository.TokenRepository
import com.teammealky.mealky.domain.usecase.SingleUseCase
import javax.inject.Inject

open class SaveTokenUseCase @Inject constructor(
        private val repo: TokenRepository
) : SingleUseCase<Token, Boolean>() {

    override fun doWork(param: Token) = repo.setToken(param)

}