package com.teammealky.mealky.data.repository

import com.teammealky.mealky.data.repository.datasource.UserLocalSource
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserDataRepository @Inject constructor(
        private val local: UserLocalSource
) : UserRepository {

    override fun clearUser(): Single<Boolean> {
        return Single.just(local.clearUser())
    }

    override fun getUser(): Single<User> {
        return local.getUser().flatMap { localUser ->
            if (localUser.id != -1) Single.just(localUser)
            else Single.just(User.defaultUser())
        }
    }

    override fun setUser(user: User): Single<Boolean> {
        return Single.just(local.setUser(user))
    }
}
