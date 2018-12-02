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
import android.app.Activity
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import com.teammealky.mealky.domain.model.Authenticator

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

    override fun showThereIsNoSuchUser() {
        infoTv.text = getString(R.string.missing_user)
    }

    override fun showNeedConfirmEmail() {
        infoTv.text = getString(R.string.you_need_to_confirm_email)
    }

    override fun showInvalidPassword() {
        infoTv.text = getString(R.string.wrong_password)
    }

    override fun isLoading(isLoading: Boolean) {
        userInputLayout.isVisible(!isLoading)
        progressBar.isVisible(isLoading)
    }

    override fun toggleSignInButton(toggle: Boolean) {
        signInBtn.isEnabled = toggle
    }

    override fun showInfoText(isVisible: Boolean) {
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

    override fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun saveUser(user: User) {
        val sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit()

        sharedPreferencesEditor.putString(Authenticator.TOKEN, user.token)
        sharedPreferencesEditor.apply()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signInBtn -> presenter?.signInButtonClicked()
            R.id.forgottenPasswordTv -> presenter?.forgottenPasswordLinkClicked()
            R.id.toSignUpTv -> presenter?.signUpLinkClicked()
        }
    }
}