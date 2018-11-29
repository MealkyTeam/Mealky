package com.teammealky.mealky.presentation.account

import android.os.Bundle
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseActivity
import com.teammealky.mealky.presentation.account.signin.SignInFragment


class AccountActivity : BaseActivity<AccountPresenter, AccountPresenter.UI, AccountViewModel>(), AccountPresenter.UI {

    override val vmClass = AccountViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(this).getComponent().inject(this)
        super.onCreate(savedInstanceState)

        supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, SignInFragment())
                .disallowAddToBackStack()
                .commit()
        /**
         * If loggedIn -> MainActivity
         * else
         * {setContentView register.
         */
    }


}