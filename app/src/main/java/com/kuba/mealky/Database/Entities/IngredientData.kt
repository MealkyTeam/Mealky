package com.kuba.mealky.Database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient", foreignKeys = arrayOf(ForeignKey(
        entity = UnitData::class,
        parentColumns = arrayOf("unit_id"),
        childColumns = arrayOf("ingredient_id")
), ForeignKey(
        entity = MealData::class,
        parentColumns = arrayOf("meal_id"),
        childColumns = arrayOf("ingredient_id")
)), indices = arrayOf(Index("name"), Index("unit_id"), Index("meal_id")))
data class IngredientData(
        @PrimaryKey(autoGenerate = true) var ingredient_id: Long?,
        @ColumnInfo(name = "meal_id") var meal_id: Long,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "quantity") var quantity: Double,
        @ColumnInfo(name = "unit_id") var unit_id: Long) {
    constructor() : this(null, 0, "", 0.0, 0)
}