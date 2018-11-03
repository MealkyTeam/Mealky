package com.teammealky.mealky.presentation.commons.injection.component

import android.app.Application
import android.content.Context
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.injection.ApplicationContext
import com.teammealky.mealky.presentation.commons.injection.module.ApplicationModule
import com.teammealky.mealky.presentation.commons.injection.module.RepositoryModule
import com.teammealky.mealky.presentation.commons.injection.module.ViewModelModule
import com.teammealky.mealky.presentation.discover.DiscoverFragment
import com.teammealky.mealky.presentation.main.MainActivity
import com.teammealky.mealky.presentation.meal.MealFragment
import com.teammealky.mealky.presentation.meals.MealListFragment
import com.teammealky.mealky.presentation.settings.SettingsFragment
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListFragment
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
    fun inject(app: ShoppingListFragment)
    fun inject(app: DiscoverFragment)
    fun inject(app: SettingsFragment)

    fun inject(app: MealFragment)
}