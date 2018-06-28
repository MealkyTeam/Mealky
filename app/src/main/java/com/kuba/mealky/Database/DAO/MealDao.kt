package com.kuba.mealky.Database.DAO

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.kuba.mealky.Database.Entities.MealData

@Dao
interface MealDao {

    @Query("SELECT * from meal")
    fun getAll(): List<MealData>

    @Insert(onConflict = REPLACE)
    fun insert(mealData: MealData)

    @Query("DELETE from meal")
    fun deleteAll()
}