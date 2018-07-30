package com.kuba.mealky.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
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