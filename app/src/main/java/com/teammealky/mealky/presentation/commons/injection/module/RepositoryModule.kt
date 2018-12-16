package com.teammealky.mealky.presentation.commons.injection.module

import com.teammealky.mealky.data.repository.ShoppingListDataRepository
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides fun shoppingListRepo(repo: ShoppingListDataRepository): ShoppingListRepository = repo

}