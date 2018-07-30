package com.kuba.mealky.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kuba.mealky.database.dao.IngredientDao
import com.kuba.mealky.database.dao.MealDao
import com.kuba.mealky.database.dao.UnitDao
import com.kuba.mealky.database.models.Ingredient
import com.kuba.mealky.database.models.Meal
import com.kuba.mealky.database.models.UnitData

@Database(entities = [(Meal::class), (Ingredient::class), (UnitData::class)], version = 1)
abstract class MealkyDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun unitDao(): UnitDao

    companion object {
        private var INSTANCE: MealkyDatabase? = null

        fun getInstance(context: Context): MealkyDatabase? {
            if (INSTANCE == null) {
                synchronized(MealkyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MealkyDatabase::class.java, "mealky.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}