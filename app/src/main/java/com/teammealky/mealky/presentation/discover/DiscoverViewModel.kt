package com.teammealky.mealky.presentation.discover

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(override val presenter: DiscoverPresenter) :
        BaseViewModel<DiscoverPresenter>(presenter)
