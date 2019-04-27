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

                MEAL_NAME_EMPTY -> ErrorType.MEAL_NAME_EMPTY
                MEAL_PREP_TIME -> ErrorType.MEAL_PREP_TIME
                MEAL_DESCRIPTION -> ErrorType.MEAL_DESCRIPTION
                MEAL_INGREDIENT -> ErrorType.MEAL_INGREDIENT
                MEAL_IMAGE -> ErrorType.MEAL_IMAGE
                MEAL_USER -> ErrorType.MEAL_USER

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
        MEAL_PREP_TIME, MEAL_DESCRIPTION,
        MEAL_INGREDIENT, MEAL_IMAGE, MEAL_USER,

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

        const val MEAL_NAME_EMPTY = "Meal name can not be empty."
        const val MEAL_PREP_TIME = "Meal preparation time lower can not be lower than 1."
        const val MEAL_DESCRIPTION = "Meal description can not be empty."
        const val MEAL_INGREDIENT = "Meal should contain atleast one ingredient."
        const val MEAL_IMAGE = "Meal should contain atleast one image, maximum five images."
        const val MEAL_USER = "This user does not exists."

        const val SOMETHING_WENT_WRONG = "Something went wrong."
        const val PARSING_API_ERROR = "Error occurred during parsing API error response."
    }
}