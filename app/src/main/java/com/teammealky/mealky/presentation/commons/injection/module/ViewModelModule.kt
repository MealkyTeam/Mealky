package com.teammealky.mealky.presentation.commons.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammealky.mealky.presentation.account.AccountViewModel
import com.teammealky.mealky.presentation.account.forgottenPassword.ForgottenPasswordViewModel
import com.teammealky.mealky.presentation.account.signin.SignInViewModel
import com.teammealky.mealky.presentation.account.signup.SignUpViewModel
import com.teammealky.mealky.presentation.addMeal.AddMealViewModel
import com.teammealky.mealky.presentation.commons.injection.ViewModelFactory
import com.teammealky.mealky.presentation.commons.injection.ViewModelKey
import com.teammealky.mealky.presentation.discover.DiscoverViewModel
import com.teammealky.mealky.presentation.main.MainViewModel
import com.teammealky.mealky.presentation.meal.MealViewModel
import com.teammealky.mealky.presentation.meals.MealListViewModel
import com.teammealky.mealky.presentation.settings.SettingsViewModel
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListViewModel
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.AddIngredientViewModel
import com.teammealky.mealky.presentation.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds @IntoMap @ViewModelKey(MainViewModel::class)
    internal abstract fun main(vm: MainViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(SplashViewModel::class)
    internal abstract fun splash(vm: SplashViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(MealListViewModel::class)
    internal abstract fun mealList(vm: MealListViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(AccountViewModel::class)
    internal abstract fun account(vm: AccountViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(ShoppingListViewModel::class)
    internal abstract fun shoppingList(vm: ShoppingListViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(AddIngredientViewModel::class)
    internal abstract fun purchaseInfoDialog(vm: AddIngredientViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(DiscoverViewModel::class)
    internal abstract fun discover(vm: DiscoverViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(SettingsViewModel::class)
    internal abstract fun settings(vm: SettingsViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(MealViewModel::class)
    internal abstract fun meal(vm: MealViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(AddMealViewModel::class)
    internal abstract fun addMeal(vm: AddMealViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(SignInViewModel::class)
    internal abstract fun signIn(vm: SignInViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(SignUpViewModel::class)
    internal abstract fun signUp(vm: SignUpViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(ForgottenPasswordViewModel::class)
    internal abstract fun forgottenPassword(vm: ForgottenPasswordViewModel): ViewModel
}