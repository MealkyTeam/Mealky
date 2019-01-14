package com.teammealky.mealky.domain.usecase.token

import com.teammealky.mealky.domain.repository.TokenRepository
import com.teammealky.mealky.domain.usecase.ParamLessUseCase
import io.reactivex.Single
import javax.inject.Inject

 class ClearTokenUseCase @Inject constructor(
        private val repo: TokenRepository
) : ParamLessUseCase<Boolean>() {

    override fun doWork(): Single<Boolean> = repo.clearToken()
}