package com.teammealky.mealky.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.BuildConfig
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.signout_layout.*

class SettingsFragment : BaseFragment<SettingsPresenter, SettingsPresenter.UI, SettingsViewModel>(), SettingsPresenter.UI, SettingsPresenter.SignOutListener {

    override val vmClass = SettingsViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.settings_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter?.setupPresenter()
    }

    override fun setupView(user: User) {
        settingInfo.text = getString(R.string.version, BuildConfig.VERSION_NAME)
        val username = user.username

        usernameTv.text = username
        if (usernameTv.text.isEmpty())
            usernameTv.isVisible(false)

        signOutCard.listener = this
    }

    override fun toAccountActivity() {
        Navigator.from(context as Navigator.Navigable).openActivity(Navigator.ACTIVITY_ACCOUNT)
        activity?.finish()
    }

    override fun signOutBtnClicked() {
        presenter?.signOutClicked()
    }

}
