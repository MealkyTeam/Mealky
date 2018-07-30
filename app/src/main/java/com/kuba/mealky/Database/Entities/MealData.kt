package com.kuba.mealky.Database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "meal", indices = arrayOf(Index("name")))
data class MealData(
        @PrimaryKey(autoGenerate = true) var meal_id: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "prep_time") var prep_time: Int,
        @ColumnInfo(name = "preparation") var preparation: String

) {
    constructor() : this(null, "", 0, "")
}