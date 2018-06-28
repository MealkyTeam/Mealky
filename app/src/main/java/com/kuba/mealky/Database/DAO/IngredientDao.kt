package com.kuba.mealky.Database.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
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