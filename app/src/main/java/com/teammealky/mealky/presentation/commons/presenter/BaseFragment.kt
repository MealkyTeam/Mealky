package com.teammealky.mealky.presentation.commons.presenter

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.teammealky.mealky.R
import timber.log.Timber
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

    override fun showErrorMessage(retry: () -> Unit, e: Throwable, cancelable: Boolean) {
        if (context == null) return

        Timber.e("KUBA_LOG Method:showErrorMessage ***** $e *****")
        alertDialog = AlertDialog.Builder(context!!)
                .setTitle(R.string.just_a_moment)
                .setMessage(R.string.service_unavailable)
                .setPositiveButton(R.string.retry) { _, _ ->
                    try {
                        retry.invoke()
                    } catch (ignored: Exception) {
                    }
                }
                .setNegativeButton(R.string.exit_app) { _, _ -> activity?.finish() }
                .setCancelable(cancelable)
                .show()
    }

    override fun hideKeyboard() {
        this.activity?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
    }
}