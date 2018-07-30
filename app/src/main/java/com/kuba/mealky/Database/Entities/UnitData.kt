package com.kuba.mealky.Database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "unit", indices = arrayOf(Index("name")))
data class UnitData(
        @PrimaryKey(autoGenerate = true) var unit_id: Long?,
        @ColumnInfo(name = "name") var name: String
) {
    constructor() : this(null, "")
}