package com.teammealky.mealky.domain.model

class APIError(override val message: String) : Exception() {

    var type: ErrorType = ErrorType.SOMETHING_WENT_WRONG
        get() {
            return when (message) {
                "This account is not confirmed." -> ErrorType.CONFIRM_EMAIL
                "Invalid token." -> ErrorType.INVALID_TOKEN
                "User with this email does not exists." -> ErrorType.NO_SUCH_USER
                "Wrong password." -> ErrorType.WRONG_PASSWORD

                "Account with this email already exists." -> ErrorType.EMAIL_TAKEN
                "Account with this username already exists." -> ErrorType.USERNAME_TAKEN

                "This email is invalid." -> ErrorType.INVALID_EMAIL
                "Username should contain only alphanumerics." -> ErrorType.INVALID_USERNAME
                "Password length should be longer than 5." -> ErrorType.INVALID_PASSWORD
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
}