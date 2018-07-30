package com.kuba.mealky.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "meal", indices = [(Index("name"))])
data class Meal(
        @PrimaryKey(autoGenerate = true) var meal_id: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "prep_time") var prep_time: Int,
        @ColumnInfo(name = "preparation") var preparation: String

) {
    constructor() : this(null, "", 0, "")
}