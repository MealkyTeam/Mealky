package com.teammealky.mealky.domain.model

class APIError(override val message: String) : Exception() {

    val type: ErrorType
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

                INVALID_MEAL_NAME -> ErrorType.MEAL_NAME_EMPTY
                INVALID_PREP_TIME -> ErrorType.INVALID_PREP_TIME
                INVALID_DESCRIPTION -> ErrorType.INVALID_DESCRIPTION
                INVALID_INGREDIENTS -> ErrorType.INVALID_INGREDIENTS
                INVALID_IMAGES -> ErrorType.INVALID_IMAGES
                USER_NOT_EXITS -> ErrorType.MEAL_USER

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

        //Add meal
        MEAL_NAME_EMPTY,
        INVALID_PREP_TIME, INVALID_DESCRIPTION,
        INVALID_INGREDIENTS, INVALID_IMAGES, MEAL_USER,

        //General:
        SOMETHING_WENT_WRONG
    }

    companion object {
        const val CONFIRM_EMAIL = "This account is not confirmed."
        const val INVALID_TOKEN = "Invalid token."
        const val NO_SUCH_USER = "User with this email does not exist."
        const val WRONG_PASSWORD = "Wrong password."

        const val EMAIL_TAKEN = "An account with this email already exists."
        const val USERNAME_TAKEN = "An account with this username already exists."
        const val INVALID_EMAIL = "This email is invalid."
        const val INVALID_USERNAME = "Username should contain only alphanumerics."
        const val INVALID_PASSWORD = "Password length should be longer than 5."

        const val INVALID_MEAL_NAME = "Meal name cannot be empty."
        const val INVALID_PREP_TIME = "Meal preparation time cannot be lower than 1."
        const val INVALID_DESCRIPTION = "Meal description cannot be empty."
        const val INVALID_INGREDIENTS = "A meal should contain at least one ingredient."
        const val INVALID_IMAGES = "A meal should contain at least one and a maximum of five images."
        const val USER_NOT_EXITS = "This user does not exist."

        const val SOMETHING_WENT_WRONG = "Something went wrong."
        const val PARSING_API_ERROR = "Error occurred during parsing API error response."
    }
}