package com.kuba.mealky.presenters

import android.content.Context
import com.kuba.mealky.database.MealkyDatabase
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