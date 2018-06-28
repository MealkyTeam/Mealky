package com.kuba.mealky.Presenters

import android.content.Context
import com.kuba.mealky.Database.MealkyDatabase
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MealkyDatabaseTest {

    @Mock
    lateinit var contextMock: Context

    @Test(expected = IllegalArgumentException::class)
    fun getInstanceTest() {
        MockitoAnnotations.initMocks(this)
        val db = MealkyDatabase.getInstance(contextMock)
    }
}