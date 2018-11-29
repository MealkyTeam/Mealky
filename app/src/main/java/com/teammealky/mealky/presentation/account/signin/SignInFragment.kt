package com.teammealky.mealky.presentation.account.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.main.MainActivity
import kotlinx.android.synthetic.main.signin_fragment.*

class SignInFragment : BaseFragment<SignInPresenter, SignInPresenter.UI, SignInViewModel>(), SignInPresenter.UI, View.OnClickListener {

    override val vmClass = SignInViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.signin_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        signInBtn.setOnClickListener(this)
        forgottenPasswordTv.setOnClickListener(this)
        toSignUpTv.setOnClickListener(this)
    }

    override fun toMainActivity() {
        Navigator.from(context as Navigator.Navigable).openActivity(Navigator.ACTIVITY_MAIN)
        activity?.finish()
    }

    override fun toSignUpFragment() {
        Navigator.from(context as Navigator.Navigable).openSignUp()
    }

    override fun toForgottenPasswordFragment() {
        Navigator.from(context as Navigator.Navigable).openForgottenPassword()
    }

    override fun showInvalidCredentials() {
        //todo implement that
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signInBtn -> presenter?.signInButtonClicked()
            R.id.forgottenPasswordTv -> presenter?.forgottenPasswordLinkClicked()
            R.id.toSignUpTv -> presenter?.signUpLinkClicked()
        }
    }
}