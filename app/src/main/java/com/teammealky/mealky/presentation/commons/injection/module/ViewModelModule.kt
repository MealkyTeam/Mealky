package com.teammealky.mealky.presentation.commons.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammealky.mealky.presentation.commons.injection.ViewModelFactory
import com.teammealky.mealky.presentation.commons.injection.ViewModelKey
import com.teammealky.mealky.presentation.main.MainViewModel
import com.teammealky.mealky.presentation.meal.MealViewModel
import com.teammealky.mealky.presentation.meals.MealListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds @IntoMap @ViewModelKey(MealListViewModel::class)
    internal abstract fun mealList(vm: MealListViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(MainViewModel::class)
    internal abstract fun main(vm: MainViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(MealViewModel::class)
    internal abstract fun meal(vm: MealViewModel): ViewModel

}