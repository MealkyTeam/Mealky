package com.kuba.mealky.presentation.commons.presenter

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<P : Presenter<V>, in V, VM : BaseViewModel<P>> : AppCompatActivity(), BaseUI {

    abstract val vmClass: Class<VM>

    protected var presenter: P? = null
    private var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ViewModelProviders.of(this).get(vmClass).presenter
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
        alertDialog = AlertDialog.Builder(this)
                .setTitle("Sorry!").setMessage("Something went wrong.")
                .setPositiveButton("Close", null)
                .show()
    }
}