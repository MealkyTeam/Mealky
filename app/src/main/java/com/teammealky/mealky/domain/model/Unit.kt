package com.teammealky.mealky.domain.model

data class Unit(
        val id: Int,
        val name: String
){
    companion object {
        fun defaultUnit(): Unit{
            return Unit(-1,"")
        }
    }
}