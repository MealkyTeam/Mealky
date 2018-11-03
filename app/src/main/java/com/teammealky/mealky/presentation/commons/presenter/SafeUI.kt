package com.teammealky.mealky.presentation.commons.presenter

interface SafeUI<out UI> {
    fun perform(command: (UI) -> Unit)
    fun performSticky(command: (UI) -> Unit)
}