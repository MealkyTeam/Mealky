package com.teammealky.mealky.presentation.account.signup

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
import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.signup_fragment.*

class SignUpFragment : BaseFragment<SignUpPresenter, SignUpPresenter.UI, SignUpViewModel>(), SignUpPresenter.UI,
        View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {

    override val vmClass = SignUpViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.signup_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        signUpBtn.setOnClickListener(this)
        toSignInTv.setOnClickListener(this)

        usernameInput.addTextChangedListener(this)
        emailInput.addTextChangedListener(this)
        passwordInput.addTextChangedListener(this)
        passwordInput.setOnEditorActionListener(this)
    }

    override fun isLoading(isLoading: Boolean) {
        userInputLayout.isVisible(!isLoading)
        progressBar.isVisible(isLoading)
    }

    override fun toSignInFragment() {
        Navigator.from(context as Navigator.Navigable).openSignIn()
    }

    override fun showErrorInInfo(error: APIError.ErrorType) {
        infoTv.text = when (error) {
            APIError.ErrorType.EMAIL_TAKEN -> getString(R.string.email_taken)
            APIError.ErrorType.USERNAME_TAKEN -> getString(R.string.username_taken)
            APIError.ErrorType.INVALID_EMAIL -> getString(R.string.incorrect_email)
            APIError.ErrorType.INVALID_USERNAME -> getString(R.string.incorrect_username)
            APIError.ErrorType.INVALID_PASSWORD -> getString(R.string.incorrect_password)
            else -> getString(R.string.something_went_wrong)
        }
    }

    override fun showInfoTv(isVisible: Boolean) {
        infoTv.isVisible(isVisible)
    }

    override fun toggleSignUpButton(toggle: Boolean) {
        signUpBtn.isEnabled = toggle
    }

    override fun toggleUsernameError(toggle: Boolean) {
        usernameInput.error = if (toggle) getString(R.string.incorrect_username) else null
    }

    override fun toggleEmailError(toggle: Boolean) {
        emailInput.error = if (toggle) getString(R.string.incorrect_email) else null
    }

    override fun togglePasswordError(toggle: Boolean) {
        passwordInput.error = if (toggle) getString(R.string.incorrect_password) else null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signUpBtn -> presenter?.signUpButtonClicked()
            R.id.toSignInTv -> presenter?.signInLinkClicked()
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        presenter?.model = User.defaultUser().copy(username = usernameInput.text.toString(),
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
            presenter?.signUpButtonClicked()
            return true
        }

        return false
    }
}