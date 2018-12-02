package com.teammealky.mealky.domain.model

class APIError(override val message: String) : Exception() {

    var type: ErrorType = ErrorType.SOMETHING_WENT_WRONG
        get() {
            return when (message) {
                "Wrong password." -> ErrorType.INVALID_PASSWORD
                "User with this email does not exists." -> ErrorType.NO_SUCH_USER
                "Invalid token." -> ErrorType.INVALID_TOKEN
                else -> ErrorType.SOMETHING_WENT_WRONG
            }
        }

    enum class ErrorType {
        CONFIRM_EMAIL, INVALID_TOKEN, NO_SUCH_USER, INVALID_PASSWORD, SOMETHING_WENT_WRONG;
    }
}