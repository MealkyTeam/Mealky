package com.kuba.mealky.Database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kuba.mealky.Database.Entities.UnitData

@Dao
interface UnitDao {

    @Query("SELECT * from unit")
    fun getAll(): MutableList<UnitData>

    @Insert(onConflict = REPLACE)
    fun insert(unitData: UnitData)

    @Query("DELETE from unit")
    fun deleteAll()
}