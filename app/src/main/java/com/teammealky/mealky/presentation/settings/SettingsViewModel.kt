package com.teammealky.mealky.presentation.settings

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import com.teammealky.mealky.presentation.discover.DiscoverPresenter
import javax.inject.Inject

class SettingsViewModel @Inject constructor(override val presenter: SettingsPresenter) :
        BaseViewModel<SettingsPresenter>(presenter)
