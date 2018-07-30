package com.kuba.mealky.Database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kuba.mealky.Database.Entities.IngredientData

@Dao
interface IngredientDao {

    @Query("SELECT * from ingredient")
    fun getAll(): MutableList<IngredientData>

    @Insert(onConflict = REPLACE)
    fun insert(ingredientData: IngredientData)

    @Query("DELETE from ingredient")
    fun deleteAll()
}