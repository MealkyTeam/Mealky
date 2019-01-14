package com.teammealky.mealky.presentation.splash

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(override val presenter: SplashPresenter) :
        BaseViewModel<SplashPresenter>(presenter)
