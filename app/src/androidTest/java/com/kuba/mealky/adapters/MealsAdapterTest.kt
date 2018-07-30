package com.kuba.mealky.adapters

import com.kuba.mealky.database.models.Meal
import org.junit.Before
import org.junit.Test

class MealsAdapterTest {
    private lateinit var adapter: MealsAdapter
    lateinit var dummyList: MutableList<Meal>
    @Before
    fun setUp() {
        dummyList = mutableListOf(
                Meal(1, "test1", 0, "testPrep1"),
                Meal(2, "test2", 0, "testPrep2"),
                Meal(3, "test3", 0, "testPrep3")
        )
        adapter = MealsAdapter(dummyList, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: Meal) {
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