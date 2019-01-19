package com.teammealky.mealky.presentation.shoppinglist.component.addingredient

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import com.teammealky.mealky.presentation.commons.presenter.Presenter
import timber.log.Timber
import javax.inject.Inject

abstract class BaseDialogFragment<P : Presenter<V>, in V, VM : BaseViewModel<P>> : androidx.fragment.app.DialogFragment(), BaseUI {

    @Inject open lateinit var vmFactory: ViewModelProvider.Factory
    abstract val vmClass: Class<VM>

    protected var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProviders.of(this, vmFactory).get(vmClass)
        presenter = vm.presenter
    }

    @Suppress("UNCHECKED_CAST")
    private fun getPresenterView(): V = this as V

    override fun onResume() {
        super.onResume()
        presenter?.attach(getPresenterView())
    }

    override fun onPause() {
        presenter?.detach()
        super.onPause()
    }

    override fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun showErrorMessage(retry: () -> Unit, e: Throwable, cancelable: Boolean) {
        Timber.tag("KUBA").v("showErrorMessage NOT IMPLEMENTED")
    }
}