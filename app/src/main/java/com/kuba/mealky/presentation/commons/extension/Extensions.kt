package com.kuba.mealky.presentation.commons.extension

fun Int.toTime(): String {
    val originalValue = this
    val numOfHours = (originalValue / 60)
    val numOfMinutes = originalValue - numOfHours * 60

    return when {
        numOfHours > 0 && numOfMinutes > 0 -> "${numOfHours}h ${numOfMinutes}m"
        numOfHours > 0 && numOfMinutes == 0 -> "${numOfHours}h"
        numOfHours == 0 && numOfMinutes > 0 -> "${numOfMinutes}m"
        else -> ""
    }
}