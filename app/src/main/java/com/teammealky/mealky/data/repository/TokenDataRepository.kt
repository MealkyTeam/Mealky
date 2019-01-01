package com.teammealky.mealky.data.repository

import com.teammealky.mealky.data.repository.datasource.TokenLocalSource
import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.repository.TokenRepository
import io.reactivex.Single
import javax.inject.Inject

class TokenDataRepository @Inject constructor(
        private val local: TokenLocalSource
) : TokenRepository {

    override fun clearToken(): Single<Boolean> {
        return Single.just(local.clearToken())
    }

    override fun getToken(): Single<Token> {
       return local.getToken()
    }

    override fun setToken(token: Token): Single<Boolean> {
        return Single.just(local.setToken(token))
    }
}
