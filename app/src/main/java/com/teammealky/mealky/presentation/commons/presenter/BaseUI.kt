package com.teammealky.mealky.presentation.commons.presenter

interface BaseUI {
    fun showErrorMessage(retry: () -> Unit, e: Throwable)
    fun hideKeyboard()
}