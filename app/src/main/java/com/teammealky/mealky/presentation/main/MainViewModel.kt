package com.teammealky.mealky.presentation.main

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(override val presenter: MainPresenter) :
        BaseViewModel<MainPresenter>(presenter)
