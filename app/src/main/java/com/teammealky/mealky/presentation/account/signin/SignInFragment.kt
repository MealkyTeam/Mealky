package com.teammealky.mealky.presentation.account.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.signin_fragment.*
import com.teammealky.mealky.domain.model.APIError

class SignInFragment : BaseFragment<SignInPresenter, SignInPresenter.UI, SignInViewModel>(),
        SignInPresenter.UI, View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {

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

        emailInput.addTextChangedListener(this)
        passwordInput.addTextChangedListener(this)
        passwordInput.setOnEditorActionListener(this)

        //todo it's not implemented yet that's why it's hidden
        forgottenPasswordTv.isVisible(false)
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

    override fun showErrorInfo(error: APIError.ErrorType) {
        infoTv.text = when (error) {
            APIError.ErrorType.NO_SUCH_USER -> getString(R.string.missing_user)
            APIError.ErrorType.CONFIRM_EMAIL -> getString(R.string.you_need_to_confirm_email)
            APIError.ErrorType.WRONG_PASSWORD -> getString(R.string.invalid_password)
            else -> getString(R.string.something_went_wrong)
        }
    }

    override fun isLoading(isLoading: Boolean) {
        userInputLayout.isVisible(!isLoading)
        progressBar.isVisible(isLoading)
    }

    override fun toggleSignInButton(toggle: Boolean) {
        signInBtn.isEnabled = toggle
    }

    override fun showInfoTv(isVisible: Boolean) {
        infoTv.isVisible(isVisible)
    }

    override fun setErrorOnEmail() {
        emailInput.error = getString(R.string.incorrect_email)
    }

    override fun hideEmailError() {
        emailInput.error = null
    }

    override fun afterTextChanged(editable: Editable?) {
        presenter?.model = User.defaultUser().copy(
                password = passwordInput.text.toString(), email = emailInput.text.toString()
        )
        presenter?.fieldsChanged()
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null && actionId == EditorInfo.IME_ACTION_DONE) {
            presenter?.signInButtonClicked()
            return true
        }

        return false
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signInBtn -> presenter?.signInButtonClicked()
            R.id.forgottenPasswordTv -> presenter?.forgottenPasswordLinkClicked()
            R.id.toSignUpTv -> presenter?.signUpLinkClicked()
        }
    }
}