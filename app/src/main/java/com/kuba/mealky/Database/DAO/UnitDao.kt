package com.kuba.mealky.Database.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
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