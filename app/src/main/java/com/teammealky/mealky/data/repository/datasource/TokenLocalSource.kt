package com.teammealky.mealky.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.presentation.commons.injection.ApplicationContext
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenLocalSource @Inject constructor(
        @ApplicationContext context: Context,
        private val serializer: Gson
) {
    private val storage: SharedPreferences by lazy {
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun clearToken(): Boolean {
        storage.edit().clear().apply()

        return true
    }

    fun setToken(token: Token): Boolean {
        val serialized = serializer.toJson(token)
        storage.edit().putString(USER, serialized).apply()

        return true
    }

    fun getToken(): Single<Token> {
        val serialized = storage.getString(USER, "") ?: ""
        if (serialized.isEmpty()) return Single.just(Token.emptyToken())
        return try {
            Single.just(serializer.fromJson(serialized, Token::class.java))
        } catch (ignored: Exception) {
            Single.just(Token.emptyToken())
        }
    }

    companion object {
        private const val STORAGE_NAME = "com.mealkyteam.token.1"
        private const val USER = "token"
    }
}
