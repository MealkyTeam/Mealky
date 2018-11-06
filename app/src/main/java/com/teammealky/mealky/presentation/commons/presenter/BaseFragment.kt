package com.teammealky.mealky.presentation.commons.presenter

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseFragment<P : Presenter<V>, in V, VM : BaseViewModel<P>> : Fragment(), BaseUI {

    @Inject open lateinit var vmFactory: ViewModelProvider.Factory
    abstract val vmClass: Class<VM>

    protected var presenter: P? = null
    private var alertDialog: AlertDialog? = null

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
        try {
            alertDialog?.dismiss()
        } catch (ignored: Exception) {

        }
        super.onPause()
    }

    override fun showErrorMessage(e: Throwable) {
        if (context == null) return
        alertDialog = AlertDialog.Builder(context!!)
                .setTitle("Sorry!").setMessage("Something went wrong.")
                .setPositiveButton("Close", null)
                .show()
    }
}