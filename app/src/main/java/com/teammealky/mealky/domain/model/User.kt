package com.teammealky.mealky.domain.model

data class User(
        val id: Int,
        val email: String?,
        val password: String?,
        //todo username and email should not be null, need change in api
        val username: String?,
        val token: String?
) {
    fun hasCorrectEmail(): Boolean {
        val regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)\$".toRegex()
        return regex.matches(email.orEmpty())
    }

    fun hasCorrectUsername(): Boolean {
        val regex = Regex.fromLiteral("^[a-zA-Z0-9]){4,15}$")

        return regex.matches(email.orEmpty())
    }

    companion object {
        fun defaultUser(): User {
            return User(0, null, null, "default_user",null)
        }
    }
}