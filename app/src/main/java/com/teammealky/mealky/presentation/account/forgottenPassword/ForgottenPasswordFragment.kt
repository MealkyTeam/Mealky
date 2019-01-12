package com.teammealky.mealky.presentation.account.forgottenPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment

class ForgottenPasswordFragment : BaseFragment<ForgottenPasswordPresenter, ForgottenPasswordPresenter.UI, ForgottenPasswordViewModel>(),
        ForgottenPasswordPresenter.UI{

    override val vmClass = ForgottenPasswordViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.forgotten_password_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
    }


    override fun isLoading(isLoading: Boolean) {

    }
}