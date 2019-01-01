package com.teammealky.mealky.domain.model

data class Unit(
        val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Unit) return false

        return other.name.toUpperCase() == this.name.toUpperCase()
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    companion object {
        fun defaultUnit(): Unit {
            return Unit("")
        }
    }
}