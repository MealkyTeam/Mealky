package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.Token
import io.reactivex.Single

interface TokenRepository {

    fun getToken(): Single<Token>

    fun setToken(token: Token): Single<Boolean>

    fun clearToken(): Single<Boolean>
}