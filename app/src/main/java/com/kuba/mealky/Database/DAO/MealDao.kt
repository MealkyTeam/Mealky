package com.kuba.mealky.Database.DAO

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.kuba.mealky.Database.Entities.MealData

@Dao
interface MealDao {

    @Query("SELECT * from meal")
    fun getAll(): MutableList<MealData>

    @Insert(onConflict = REPLACE)
    fun insert(mealData: MealData)

    @Query("DELETE from meal")
    fun deleteAll()

    @Delete()
    fun delete(meal: MealData)

    @Query("SELECT * from meal WHERE meal_id=:id LIMIT 1")
    fun findMealByiD(id: Int): MealData
}