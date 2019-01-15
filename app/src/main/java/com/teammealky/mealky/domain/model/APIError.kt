package com.teammealky.mealky.domain.model

class APIError(override val message: String) : Exception() {

    var type: ErrorType = ErrorType.SOMETHING_WENT_WRONG
        get() {
            return when (message) {
                CONFIRM_EMAIL -> ErrorType.CONFIRM_EMAIL
                INVALID_TOKEN -> ErrorType.INVALID_TOKEN
                NO_SUCH_USER -> ErrorType.NO_SUCH_USER
                WRONG_PASSWORD -> ErrorType.WRONG_PASSWORD

                EMAIL_TAKEN -> ErrorType.EMAIL_TAKEN
                USERNAME_TAKEN -> ErrorType.USERNAME_TAKEN
                INVALID_EMAIL -> ErrorType.INVALID_EMAIL
                INVALID_USERNAME -> ErrorType.INVALID_USERNAME
                INVALID_PASSWORD -> ErrorType.INVALID_PASSWORD

                else -> ErrorType.SOMETHING_WENT_WRONG
            }
        }

    enum class ErrorType {
        //Sign in:
        CONFIRM_EMAIL,
        INVALID_TOKEN, NO_SUCH_USER, WRONG_PASSWORD,

        //Sign up:
        EMAIL_TAKEN,
        USERNAME_TAKEN,
        INVALID_EMAIL, INVALID_USERNAME, INVALID_PASSWORD,

        //General:
        SOMETHING_WENT_WRONG
    }

    companion object {
        const val CONFIRM_EMAIL = "This account is not confirmed."
        const val INVALID_TOKEN = "Invalid token."
        const val NO_SUCH_USER = "User with this email does not exists."
        const val WRONG_PASSWORD = "Wrong password."

        const val EMAIL_TAKEN = "Account with this email already exists."
        const val USERNAME_TAKEN = "Account with this username already exists."
        const val INVALID_EMAIL = "This email is invalid."
        const val INVALID_USERNAME = "Username should contain only alphanumerics."
        const val INVALID_PASSWORD = "Password length should be longer than 5."

        const val SOMETHING_WENT_WRONG = "Something went wrong."
        const val PARSING_API_ERROR = "Error occurred during parsing API error response."
    }
}