package com.kuba.mealky.Database.Entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "unit", indices = arrayOf(Index("name")))
data class UnitData(
        @PrimaryKey(autoGenerate = true) var unit_id: Long?,
        @ColumnInfo(name = "name") var name: String
) {
    constructor() : this(null, "")
}