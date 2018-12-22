package com.teammealky.mealky.presentation.commons.presenter

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.teammealky.mealky.R
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<P : Presenter<V>, in V, VM : BaseViewModel<P>> : AppCompatActivity(), BaseUI {

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


    override fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun showErrorMessage(retry: () -> Unit, e: Throwable) {

        Timber.d("KUBA Method:showErrorMessage ***** $e *****")
        alertDialog = AlertDialog.Builder(this)
                .setTitle(R.string.just_a_moment)
                .setMessage(R.string.service_unavailable)
                .setPositiveButton(R.string.retry) { _, _ ->
                    try {
                        retry.invoke()
                    } catch (ignored: Exception) {
                    }
                }
                .setNegativeButton(R.string.exit_app) { _, _ -> this.finish() }
                .setCancelable(false)
                .show()
    }
}