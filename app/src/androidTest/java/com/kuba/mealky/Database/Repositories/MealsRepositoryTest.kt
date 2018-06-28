package com.kuba.mealky.Database.Repositories

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.kuba.mealky.Database.DAO.MealDao
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class MealsRepositoryTest {
    lateinit var mealDao: MealDao
    lateinit var database: MealkyDatabase
    lateinit var repository: MealsRepository


    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, MealkyDatabase::class.java).build()
        mealDao = database.mealDao()
        repository = MealsRepository(database)
    }

    @Test
    fun getAllIfTableIsEmpty() {
        val emptyList = emptyList<MealData>()
        val listFromRepo = repository.getAll()
        assertEquals(emptyList, listFromRepo)
    }

    @Test
    fun getAllIfTableNotEmpty() {
        val dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                MealData(2, "test2", 0, "testPrep2"),
                MealData(3, "test3", 0, "testPrep3")
        )
        repository.insertList(dummyList)
        val listFromRepo = repository.getAll()
        assertEquals(dummyList, listFromRepo)
    }

    @Test
    fun insertShouldReplace() {
        val dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                MealData(2, "test2", 0, "testPrep2"),
                MealData(3, "test3", 0, "testPrep3")
        )
        repository.insertList(dummyList)

        val editedMeal = MealData(2, "test2EDITED", 0, "testPrep2EDITED")
        dummyList.removeAt(1)
        dummyList.add(1, editedMeal)
        repository.insert(editedMeal)

        val listFromRepo = repository.getAll()
        assertEquals(dummyList, listFromRepo)
    }

    @Test
    fun deleteByMealIfExistsTest() {
        val mealNo2 = MealData(2, "test2", 0, "testPrep2")

        val dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                mealNo2,
                MealData(3, "test3", 0, "testPrep3")
        )
        repository.insertList(dummyList)
        dummyList.remove(mealNo2)
        repository.delete(mealNo2)

        val listFromRepo = repository.getAll()
        assertEquals(dummyList, listFromRepo)
    }

    @Test
    fun deleteByIdIfExistsTest() {
        val mealNo2 = MealData(2, "test2", 0, "testPrep2")

        val dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                mealNo2,
                MealData(3, "test3", 0, "testPrep3")
        )
        repository.insertList(dummyList)
        dummyList.remove(mealNo2)
        repository.delete(2)

        val listFromRepo = repository.getAll()
        assertEquals(dummyList, listFromRepo)
    }

    @Test
    fun deleteByMealIfNotExistsTest() {
        val mealNo2 = MealData(2, "test2", 0, "testPrep2")

        val dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                mealNo2,
                MealData(3, "test3", 0, "testPrep3")
        )

        val mealOutsideList = MealData(4, "test4", 0, "testPrep4")
        repository.insertList(dummyList)
        repository.delete(mealOutsideList)

        val listFromRepo = repository.getAll()
        assertEquals(dummyList, listFromRepo)
    }

    @Test
    fun deleteByIdIfNotExistsTest() {
        val mealNo2 = MealData(2, "test2", 0, "testPrep2")

        val dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                mealNo2,
                MealData(3, "test3", 0, "testPrep3")
        )
        repository.insertList(dummyList)
        repository.delete(4)

        val listFromRepo = repository.getAll()
        assertEquals(dummyList, listFromRepo)
    }

    @Test
    fun findByIdTest() {
        val mealNo2 = MealData(2, "test2", 0, "testPrep2")

        val dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                mealNo2,
                MealData(3, "test3", 0, "testPrep3")
        )
        repository.insertList(dummyList)

        assertEquals(dummyList[1], repository.findById(2))
        assertEquals(null, repository.findById(4))
    }

    @After
    fun tearDown() {
        database.close()
    }
}