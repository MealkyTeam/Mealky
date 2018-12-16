package com.teammealky.mealky.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.commons.injection.ApplicationContext
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalSource @Inject constructor(
        @ApplicationContext context: Context,
        private val serializer: Gson
) {
    private val storage: SharedPreferences by lazy {
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun clearUser(): Boolean {
        storage.edit().clear().apply()

        return true
    }

    fun setUser(user: User): Boolean {
        clearUser()

        val serialized = serializer.toJson(user)
        storage.edit().putString(USER, serialized).apply()

        return true
    }

    fun getUser(): Single<User> {
        val serialized = storage.getString(USER, "") ?: ""
        if (serialized.isEmpty()) return Single.just(User.defaultUser())

        return try {
            Single.just(serializer.fromJson(serialized, User::class.java))
        } catch (ignored: Exception) {
            Single.just(User.defaultUser())
        }
    }

    companion object {
        private const val STORAGE_NAME = "com.mealkyteam.user.1"
        private const val USER = "user"
    }
}
