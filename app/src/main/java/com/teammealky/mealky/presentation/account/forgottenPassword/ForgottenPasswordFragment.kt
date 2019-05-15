package com.teammealky.mealky.presentation.account.forgottenPassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.forgotten_password_fragment.*

class ForgottenPasswordFragment : BaseFragment<ForgottenPasswordPresenter, ForgottenPasswordPresenter.UI, ForgottenPasswordViewModel>(),
        ForgottenPasswordPresenter.UI, View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {

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
        resetPasswordBtn.setOnClickListener(this)
        toSignInTv.setOnClickListener(this)

        emailInput.addTextChangedListener(this)
        emailInput.setOnEditorActionListener(this)
    }

    override fun isLoading(isLoading: Boolean) {
        userInputLayout.isVisible(!isLoading)
        progressBar.isVisible(isLoading)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.resetPasswordBtn -> presenter?.onResetBtnClicked()
            R.id.toSignInTv -> presenter?.signInLinkClicked()
        }
    }

    override fun showErrorInfo(error: APIError.ErrorType) {
        infoTv.text = when (error) {
            APIError.ErrorType.NO_SUCH_USER -> getString(R.string.missing_user)
            else -> getString(R.string.something_went_wrong)
        }
    }

    override fun showToast() {
        val message = getString(R.string.password_reset)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun toSignInFragment() {
        Navigator.from(context as Navigator.Navigable).openSignIn()
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

    override fun toggleResetPasswordButton(isToggled: Boolean) {
        resetPasswordBtn.isEnabled = isToggled
    }

    override fun afterTextChanged(editable: Editable?) {
        presenter?.model = User.defaultUser().copy(
                email = emailInput.text.toString()
        )
        presenter?.fieldsChanged()
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null && actionId == EditorInfo.IME_ACTION_DONE) {
            presenter?.onResetBtnClicked()
            return true
        }

        return false
    }
}