package com.teammealky.mealky.presentation.addmeal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.add_meal_fragment.*
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult
import com.teammealky.mealky.presentation.commons.extension.isInvisible
import com.teammealky.mealky.presentation.commons.extension.isVisible
import kotlinx.android.synthetic.main.meals_fragment.*


class AddMealFragment : BaseFragment<AddMealPresenter, AddMealPresenter.UI, AddMealViewModel>(),
        AddMealPresenter.UI, View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {

    override val vmClass = AddMealViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(com.teammealky.mealky.R.layout.add_meal_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setupEditTexts()
        setupOnClick()

        scrollView.setOnTouchListener { _, _ ->
            presenter?.touchedOutside()
            false
        }
    }

    private fun setupOnClick() {
        confirmBtn.setOnClickListener(this)
        addImagesTv.setOnClickListener(this)
        addIngredientsTv.setOnClickListener(this)
    }

    private fun setupEditTexts() {
        titleInput.addTextChangedListener(this)

        preparationTimeInput.addTextChangedListener(this)
        preparationTimeInput.setOnEditorActionListener(this)

        preparationInput.addTextChangedListener(this)
    }

    override fun enableConfirmBtn(isEnabled: Boolean) {
        confirmBtn.isEnabled = isEnabled
    }

    override fun toMealsFragment() {
        Navigator.from(context as Navigator.Navigable).openHome()
    }

    override fun showErrors(errors: List<ValidationResult>) {
        errors.forEach { error ->
            when (error) {
                TITLE_ERROR -> {
                    titleInput.error = getString(R.string.title_error)
                }
                PREP_ERROR -> {
                    preparationInput.error = getString(R.string.prep_error)
                }
                PREP_TIME_ERROR -> {
                    preparationTimeInput.error = getString(R.string.prep_time_error)
                }
                INGREDIENTS_ERROR -> {
                    ingredientsErrorTv.isVisible(true)
                }
                IMAGES_ERROR -> {
                    imagesErrorTv.isVisible(true)
                }

                else -> {

                }
            }
        }
    }

    override fun clearErrors() {
        titleInput.error = null
        preparationInput.error = null
        preparationTimeInput.error = null
        ingredientsErrorTv.isVisible(false)
        imagesErrorTv.isVisible(false)
    }

    override fun showToast() {
        Toast.makeText(context, getString(R.string.meal_added), Toast.LENGTH_LONG).show()
    }

    override fun isLoading(isLoading: Boolean) {
        addMealLayout.isInvisible(isLoading)
        progressBar.isVisible(!isLoading)
    }

    override fun afterTextChanged(editable: Editable?) {
        presenter?.fieldsChanged(titleInput.text?.toString(), preparationTimeInput.text?.toString(), preparationInput.text?.toString())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.confirmBtn -> presenter?.confirmBtnClicked()
            R.id.addImagesTv -> presenter?.addImagesBtnClicked()
            R.id.addIngredientsTv -> presenter?.addIngredientsBtnClicked()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null && actionId == EditorInfo.IME_ACTION_NEXT) {
            preparationInput.requestFocus()

            return true
        }

        return false
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}

