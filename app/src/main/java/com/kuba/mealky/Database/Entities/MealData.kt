package com.kuba.mealky.Database.Entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "meal", indices = arrayOf(Index("name")))
data class MealData(
        @PrimaryKey(autoGenerate = true) var meal_id: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "prep_time") var prep_time: Int,
        @ColumnInfo(name = "preparation") var preparation: String

) {
    constructor() : this(null, "", 0, "")
}