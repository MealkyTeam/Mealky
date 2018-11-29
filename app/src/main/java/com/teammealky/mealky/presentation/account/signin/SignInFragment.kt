package com.teammealky.mealky.presentation.account.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
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
    }

    override fun onClick(view: View?) {
        //todo add abstraction. Now is only for test
        when (view?.id) {
            R.id.signInBtn -> {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }
}