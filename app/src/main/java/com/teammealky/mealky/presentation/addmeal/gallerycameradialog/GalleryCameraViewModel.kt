package com.teammealky.mealky.presentation.addmeal.gallerycameradialog

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class GalleryCameraViewModel @Inject constructor(
        override val presenter: GalleryCameraPresenter
) : BaseViewModel<GalleryCameraPresenter>(presenter)
