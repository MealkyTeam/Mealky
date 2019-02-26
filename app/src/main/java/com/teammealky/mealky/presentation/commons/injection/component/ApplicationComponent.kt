package com.teammealky.mealky.presentation.commons.injection.component

import android.app.Application
import android.content.Context
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.account.AccountActivity
import com.teammealky.mealky.presentation.account.forgottenPassword.ForgottenPasswordFragment
import com.teammealky.mealky.presentation.account.signin.SignInFragment
import com.teammealky.mealky.presentation.account.signup.SignUpFragment
import com.teammealky.mealky.presentation.addMeal.AddMealFragment
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
import com.teammealky.mealky.presentation.commons.component.addingredient.view.AddIngredientDialog
import com.teammealky.mealky.presentation.splash.SplashActivity
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

    fun meals(): MealsRepository
    fun authorization(): AuthorizationRepository

    fun inject(navigator: Navigator)
    fun inject(app: App)

    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: AccountActivity)

    fun inject(app: MealListFragment)
    fun inject(app: ShoppingListFragment)
    fun inject(app: DiscoverFragment)
    fun inject(app: SettingsFragment)
    fun inject(app: SignInFragment)
    fun inject(app: SignUpFragment)
    fun inject(app: ForgottenPasswordFragment)

    fun inject(app: MealFragment)
    fun inject(app: AddMealFragment)

    fun inject(dialog: AddIngredientDialog)

}