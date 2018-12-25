package com.teammealky.mealky.presentation.shoppinglist.component.addingredient.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.AddIngredientPresenter
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.AddIngredientViewModel
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.BaseDialogFragment

class AddIngredientDialog : BaseDialogFragment<AddIngredientPresenter, AddIngredientPresenter.UI, AddIngredientViewModel>(),
        AddIngredientPresenter.UI, View.OnClickListener {

    override val vmClass = AddIngredientViewModel::class.java

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.add_ingredient_dialog, null)
        return AlertDialog.Builder(activity!!)
                .setView(view)
                .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

//        val dialog = dialog as AlertDialog
//        dialog.setCancelable(false)
//        positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//        positiveButton?.setOnClickListener(this)
//        toggleButton(positiveButton, false)
//
//        negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//        negativeButton?.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
//
//        getDialog()?.window?.clearFlags(
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                        or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
//        )
//        getDialog()?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//
//        recyclerView?.requestFocus()
    }

    override fun onClick(v: View?) {
//        presenter?.validate()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

//        context?.let { c -> getActivityFromContext(c)?.let { hideSoftwareKeyboard(it) } }
//
//        if (targetFragment?.isAdded == true) return
//        if (targetFragment is DialogInterface.OnDismissListener)
//            (targetFragment as DialogInterface.OnDismissListener).onDismiss(dialog)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        val list = presenter?.models ?: emptyList()
//
//        outState.putAll(PurchaseInfoMapper.writePurchaseInfo(list))
    }

//    companion object {
//        fun newInstance(model: AddIngredientViewModel): AddIngredientDialog{
//            //todo pass that model
//            return AddIngredientDialog()
//        }
//    }

}
