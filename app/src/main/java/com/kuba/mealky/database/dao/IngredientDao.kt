package com.kuba.mealky.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kuba.mealky.database.models.Ingredient

@Dao
interface IngredientDao {

    @Query("SELECT * from ingredient")
    fun getAll(): MutableList<Ingredient>

    @Insert(onConflict = REPLACE)
    fun insert(ingredientData: Ingredient)

    @Query("DELETE from ingredient")
    fun deleteAll()
}