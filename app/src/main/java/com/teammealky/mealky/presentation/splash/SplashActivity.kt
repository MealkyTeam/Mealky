package com.teammealky.mealky.presentation.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.presenter.BaseActivity
import timber.log.Timber

class SplashActivity : BaseActivity<SplashPresenter, SplashPresenter.UI, SplashViewModel>(), SplashPresenter.UI, Navigator.Navigable {

    override val vmClass = SplashViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(this).getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun toSignIn() {
        Navigator.from(getContext() as Navigator.Navigable).openActivity(Navigator.ACTIVITY_ACCOUNT)
        this.finish()
    }

    override fun toMainActivity() {
        Navigator.from(getContext() as Navigator.Navigable).openActivity(Navigator.ACTIVITY_MAIN)
        this.finish()
    }

    override fun getContext(): Context = this

    override fun navigateTo(fragment: Fragment, cleanStack: Boolean) {
        Timber.d("KUBA_LOG Method:navigateTo ***** NOT IMPLEMENTED *****")
    }
}