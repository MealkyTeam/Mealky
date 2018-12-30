package com.teammealky.mealky.presentation.shoppinglist.component.addingredient.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.extension.isInvisible
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.AddIngredientPresenter
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.AddIngredientViewModel
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.BaseDialogFragment
import kotlinx.android.synthetic.main.add_ingredient_dialog.*
import java.lang.Exception

class AddIngredientDialog : BaseDialogFragment<AddIngredientPresenter, AddIngredientPresenter.UI, AddIngredientViewModel>(),
        AddIngredientPresenter.UI, View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {

    override val vmClass = AddIngredientViewModel::class.java
    private var ingredientsAdapter: ArrayAdapter<String>? = null
    private var unitsAdapter: ArrayAdapter<String>? = null

    var ingredientInput: AutoCompleteTextView? = null
    var quantityInput: TextInputEditText? = null
    var unitInput: AutoCompleteTextView? = null
    var progressBar: ProgressBar? = null
    var userInputLayout: LinearLayout? = null

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.add_ingredient_dialog, null)
        return AlertDialog.Builder(activity!!)
                .setView(view)
                .create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        ingredientInput = dialog.ingredientInput
        quantityInput = dialog.quantityInput
        unitInput = dialog.unitInput
        progressBar = dialog.progressBar
        userInputLayout = dialog.userInputLayout

        ingredientInput?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }
        ingredientInput?.requestFocus()


        setupView()
    }

    private fun setupView() {
        setupTextListeners()
        dialog.addIngredientBtn.setOnClickListener(this)

        presenter?.setupFinished()
    }

    override fun setupAutocompleteAdapters(ingredients: List<Ingredient>, units: List<Unit>) {
        if (context == null) return
        ingredientsAdapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, ingredients.map { it.name.capitalize() })
        unitsAdapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, units.map { it.name })

        ingredientInput?.setAdapter(ingredientsAdapter)
        unitInput?.setAdapter(unitsAdapter)
    }

    private fun setupTextListeners() {
        ingredientInput?.addTextChangedListener(this)
        quantityInput?.addTextChangedListener(this)
        unitInput?.addTextChangedListener(this)
        unitInput?.setOnEditorActionListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addIngredientBtn -> presenter?.addIngredientBtnClicked()
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        try {
            presenter?.model = Ingredient.defaultIngredient().copy(
                    name = ingredientInput?.text.toString(),
                    quantity = quantityInput?.text.toString().toDouble(),
                    unit = Unit.defaultUnit().copy(name = unitInput?.text.toString())
            )
        } catch (ignored: Exception) {
        }
        presenter?.fieldsChanged()
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null && actionId == EditorInfo.IME_ACTION_DONE) {
            presenter?.addIngredientBtnClicked()
            return true
        }

        return false
    }

    override fun toggleAddButton(isToggled: Boolean) {
        dialog.addIngredientBtn.isEnabled = isToggled
    }

    override fun addIngredient(ingredient: Ingredient) {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        if (targetFragment is AddIngredientListener) {
            (targetFragment as AddIngredientListener).onInformationPassed(presenter?.model!!)
        }

        dismiss()
    }

    override fun isLoading(isLoading: Boolean) {
        progressBar?.isVisible(isLoading)
        userInputLayout?.isInvisible(isLoading)
    }

    interface AddIngredientListener {
        fun onInformationPassed(ingredient: Ingredient)
    }

}
