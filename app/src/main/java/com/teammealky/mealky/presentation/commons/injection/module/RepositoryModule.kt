package com.teammealky.mealky.presentation.commons.injection.module

import com.teammealky.mealky.data.repository.ShoppingListDataRepository
import com.teammealky.mealky.data.repository.TokenDataRepository
import com.teammealky.mealky.data.repository.UserDataRepository
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.repository.TokenRepository
import com.teammealky.mealky.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides fun shoppingListRepo(repo: ShoppingListDataRepository): ShoppingListRepository = repo
    @Provides fun userRepo(repo: UserDataRepository): UserRepository = repo
    @Provides fun tokenRepo(repo: TokenDataRepository): TokenRepository = repo
}