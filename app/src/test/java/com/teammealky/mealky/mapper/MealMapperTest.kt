package com.teammealky.mealky.mapper

import android.os.Build
import com.teammealky.mealky.MockDataTest
import org.junit.Test
import com.teammealky.mealky.presentation.App
import org.junit.Assert.*
import com.teammealky.mealky.presentation.meal.MealMapper
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = App::class, sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class MealMapperTest {

    @Test
    fun readFromBundle() {
        val meal1 = MockDataTest.MEALS[0]
        val bundle1 = MealMapper.writeExtra(meal1)
        val mealFromBundle1 = MealMapper.readExtra(bundle1)

        val meal2 = MockDataTest.MEALS[1]
        val bundle2 = MealMapper.writeExtra(meal2)
        val mealFromBundle2 = MealMapper.readExtra(bundle2)

        val meal3 = MockDataTest.MEALS[2]
        val bundle3 = MealMapper.writeExtra(meal3)
        val mealFromBundle3 = MealMapper.readExtra(bundle3)

        assertEquals(meal1, mealFromBundle1)
        assertEquals(meal2, mealFromBundle2)
        assertEquals(meal3, mealFromBundle3)
    }
}