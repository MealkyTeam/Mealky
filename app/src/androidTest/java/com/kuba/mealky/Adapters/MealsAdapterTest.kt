package com.kuba.mealky.Adapters

import com.kuba.mealky.Database.Entities.MealData
import org.junit.Before
import org.junit.Test

class MealsAdapterTest {
    private lateinit var adapter: MealsAdapter
    lateinit var dummyList: MutableList<MealData>
    @Before
    fun setUp() {
        dummyList = mutableListOf(
                MealData(1, "test1", 0, "testPrep1"),
                MealData(2, "test2", 0, "testPrep2"),
                MealData(3, "test3", 0, "testPrep3")
        )
        adapter = MealsAdapter(dummyList, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: MealData) {
            }
        })
    }

    @Test
    fun onCreateViewHolder() {
        assert(true)
    }

    @Test
    fun onBindViewHolder() {
        assert(true)
    }

    @Test
    fun getItemCount() {
        assert(true)

    }

    @Test
    fun getItem() {
        assert(true)
    }

    @Test
    fun removeAtTest() {
        assert(true)
    }

}