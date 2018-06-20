package com.kuba.mealky.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.kuba.mealky.Database.DAO.IngredientDao
import com.kuba.mealky.Database.DAO.MealDao
import com.kuba.mealky.Database.DAO.UnitDao
import com.kuba.mealky.Database.Entities.IngredientData
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.Entities.UnitData

@Database(entities = arrayOf(MealData::class, IngredientData::class, UnitData::class), version = 1)
abstract class MealkyDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun unitDao(): UnitDao

    companion object {
        private var INSTANCE: MealkyDatabase? = null

        fun getInstance(context: Context): MealkyDatabase? {
            if (INSTANCE == null) {
                synchronized(MealkyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
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