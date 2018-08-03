package com.kuba.mealky.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kuba.mealky.database.models.Meal
import io.reactivex.Single

@Dao
interface MealDao {

    @Query("SELECT * from meal")
    fun getAll(): Single<List<Meal>>

    @Insert(onConflict = REPLACE)
    fun insert(meal: Meal)

    @Query("DELETE from meal")
    fun deleteAll()

    @Delete
    fun delete(meal: Meal)

    @Query("DELETE from meal WHERE meal_id=:id")
    fun delete(id: Int)

    @Query("SELECT * from meal WHERE meal_id=:id LIMIT 1")
    fun findMealByiD(id: Int): Single<Meal>
}