package com.teammealky.mealky.presentation.settings

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
) : BasePresenter<SettingsPresenter.UI>() {

    private fun clearUserData(){
        //todo neet to be implemented
        Timber.e("FunName:clearUserData *****need to be implemented *****")
    }

    fun signOutClicked(){
        clearUserData()
        ui().perform { it.toAccountActivity() }
    }

    interface SignOutListener{
        fun signOutBtnClicked()
    }

    interface UI : BaseUI {
        fun toAccountActivity()
    }
}