package com.teammealky.mealky.domain.model


data class Token(val token: String){
    companion object {
        fun emptyToken() = Token("")
    }
}
