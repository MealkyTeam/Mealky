package com.kuba.mealky.presentation.commons.presenter

interface Presenter<in UI>{
    fun attach(ui: UI)
    fun detach()
    fun destroy()
}