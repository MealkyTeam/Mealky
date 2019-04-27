package com.teammealky.mealky.presentation.commons.injection.module

import com.teammealky.mealky.data.repository.AddMealDataRepository
import com.teammealky.mealky.data.repository.AuthorizationDataRepository
import com.teammealky.mealky.data.repository.IngredientsDataRepository
import com.teammealky.mealky.data.repository.MealsDataRepository
import com.teammealky.mealky.data.repository.ShoppingListDataRepository
import com.teammealky.mealky.data.repository.TokenDataRepository
import com.teammealky.mealky.data.repository.UnitsDataRepository
import com.teammealky.mealky.data.repository.UserDataRepository
import com.teammealky.mealky.domain.repository.AddMealRepository
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.repository.IngredientsRepository
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.repository.TokenRepository
import com.teammealky.mealky.domain.repository.UnitsRepository
import com.teammealky.mealky.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides fun shoppingListRepo(repo: ShoppingListDataRepository): ShoppingListRepository = repo
    @Provides fun userRepo(repo: UserDataRepository): UserRepository = repo
    @Provides fun tokenRepo(repo: TokenDataRepository): TokenRepository = repo
    @Provides fun ingredientRepo(repo: IngredientsDataRepository): IngredientsRepository = repo
    @Provides fun unitRepo(repo: UnitsDataRepository): UnitsRepository = repo
    @Provides fun provideMealsRepo(repo: MealsDataRepository): MealsRepository = repo
    @Provides fun provideAuthRepo(repo: AuthorizationDataRepository): AuthorizationRepository = repo
    @Provides fun provideAddMealRepo(repo: AddMealDataRepository): AddMealRepository = repo
}