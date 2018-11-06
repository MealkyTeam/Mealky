package com.teammealky.mealky.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.BuildConfig
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : BaseFragment<SettingsPresenter, SettingsPresenter.UI, SettingsViewModel>(), SettingsPresenter.UI {

    override val vmClass = SettingsViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.settings_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        settingInfo.text = getString(R.string.version, BuildConfig.VERSION_NAME)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }
}
