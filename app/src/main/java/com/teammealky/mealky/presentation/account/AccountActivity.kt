package com.teammealky.mealky.presentation.account

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseActivity
import com.teammealky.mealky.presentation.account.signin.SignInFragment
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import kotlinx.android.synthetic.main.activity_account.*
import java.lang.Exception

class AccountActivity : BaseActivity<AccountPresenter, AccountPresenter.UI, AccountViewModel>(), AccountPresenter.UI, Navigator.Navigable {

    override val vmClass = AccountViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(this).getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        setupView()
    }

    private fun setupView() {
        presenter?.validateToken()
    }

    override fun toSignIn() {
        mainLogo.isVisible(false)
        try {
            containerAccount.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } catch (ignored: Exception) {
        }
        setContent(SignInFragment())
    }

    override fun toMainActivity() {
        Navigator.from(getContext() as Navigator.Navigable).openActivity(Navigator.ACTIVITY_MAIN)
        this.finish()
    }

    override fun getContext(): Context = this

    override fun navigateTo(fragment: Fragment, cleanStack: Boolean) {
        presenter?.setContent(fragment, cleanStack)
    }

    override fun setContent(fragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>?) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.setReorderingAllowed(true)
        ft.replace(R.id.containerAccount, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count <= 1)
            finish()
        else
            super.onBackPressed()
    }
}