package com.kuba.mealky

import android.content.Context
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MealkyDatabaseTest {

    @Mock
    lateinit var contextMock: Context

    @Test(expected = IllegalArgumentException::class)
    fun getInstanceTest() {
        MockitoAnnotations.initMocks(this)
    }
}