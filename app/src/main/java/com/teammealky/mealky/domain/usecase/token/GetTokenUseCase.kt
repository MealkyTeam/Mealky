package com.teammealky.mealky.domain.usecase.token

import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.repository.TokenRepository
import com.teammealky.mealky.domain.usecase.ParamLessUseCase
import io.reactivex.Single
import javax.inject.Inject

open class GetTokenUseCase @Inject constructor(
        private val repo: TokenRepository
) : ParamLessUseCase<Token>() {

    override fun doWork(): Single<Token> = repo.getToken()
}