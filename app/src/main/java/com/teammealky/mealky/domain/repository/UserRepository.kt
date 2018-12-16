package com.teammealky.mealky.domain.repository

import com.teammealky.mealky.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun getUser(): Single<User>

    fun setUser(user: User): Single<Boolean>

    fun clearUser(): Single<Boolean>
}