package com.kuba.mealky.presentation.commons.injection.component

import android.app.Application
import android.content.Context
import com.kuba.mealky.presentation.App
import com.kuba.mealky.presentation.commons.Navigator
import com.kuba.mealky.presentation.commons.injection.ApplicationContext
import com.kuba.mealky.presentation.commons.injection.module.ApplicationModule
import com.kuba.mealky.presentation.commons.injection.module.RepositoryModule
import com.kuba.mealky.presentation.commons.injection.module.ViewModelModule
import com.kuba.mealky.presentation.main.MainActivity
import com.kuba.mealky.presentation.meal.MealFragment
import com.kuba.mealky.presentation.meals.MealListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ApplicationModule::class),
    (ViewModelModule::class),
    (RepositoryModule::class)
])
interface ApplicationComponent {

    @ApplicationContext fun context(): Context
    fun application(): Application

    fun inject(navigator: Navigator)
    fun inject(app: App)

    fun inject(activity: MainActivity)

    fun inject(app: MealListFragment)
    fun inject(app: MealFragment)

}