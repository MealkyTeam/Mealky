package com.kuba.mealky.presentation.main

import com.kuba.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(override val presenter: MainPresenter) :
        BaseViewModel<MainPresenter>(presenter)
