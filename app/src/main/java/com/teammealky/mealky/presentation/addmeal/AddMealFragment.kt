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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.add_meal_fragment.*
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult
import com.teammealky.mealky.presentation.commons.extension.isInvisible
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.presentation.addmeal.adapter.AddedIngredientsAdapter
import com.teammealky.mealky.presentation.addmeal.gallerycameradialog.GalleryCameraDialog
import com.teammealky.mealky.presentation.addmeal.model.ThumbnailImage
import com.teammealky.mealky.presentation.addmeal.view.AddMealThumbnailsView
import com.teammealky.mealky.presentation.commons.component.addingredient.AddIngredientDialog
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import kotlinx.android.synthetic.main.shopping_list_fragment.*


class AddMealFragment : BaseFragment<AddMealPresenter, AddMealPresenter.UI, AddMealViewModel>(),
        AddMealPresenter.UI, View.OnClickListener, TextWatcher, TextView.OnEditorActionListener,
        AddIngredientDialog.AddIngredientListener, GalleryCameraDialog.GalleryCameraListener, AddMealThumbnailsView.OnImageDeleteListener, ShoppingListAdapter.FieldChangedListener, IngredientsAdapter.OnItemClickListener {

    override val vmClass = AddMealViewModel::class.java
    private var addIngredientDialog: AddIngredientDialog? = null
    private var galleryCameraDialog: GalleryCameraDialog? = null
    private lateinit var adapter: AddedIngredientsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)

        dialogRestoration(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.add_meal_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setupEditTexts()
        setupOnClick()
        addMealThumbnailsView.deleteListener = this
        setupRecyclerView()

        scrollView.setOnTouchListener { _, _ ->
            presenter?.touchedOutside()
            false
        }
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        ingredientListRv.setHasFixedSize(true)
        ingredientListRv.layoutManager = layoutManager
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
        addMealProgressBar.isVisible(!isLoading)
    }

    override fun showAddIngredientDialog(ingredients: List<Ingredient>) {
        addIngredientDialog = AddIngredientDialog.newInstance(ingredients)
        addIngredientDialog?.setTargetFragment(this, ADD_DIALOG_ID)
        addIngredientDialog?.show(fragmentManager, ADD_DIALOG)
    }

    override fun showGalleryCameraDialog() {
        galleryCameraDialog = GalleryCameraDialog()
        galleryCameraDialog?.setTargetFragment(this, GALLERY_DIALOG_ID)
        galleryCameraDialog?.show(fragmentManager, GALLERY_DIALOG)
    }

    private fun dialogRestoration(savedInstanceState: Bundle?) {
        if (null != savedInstanceState) {
            val prevDialog = childFragmentManager.findFragmentByTag(ADD_DIALOG)
            if (prevDialog is AddIngredientDialog) {
                addIngredientDialog = prevDialog
                addIngredientDialog?.setTargetFragment(this, ADD_DIALOG_ID)
            }
        }
    }

    override fun showImagesQueue(attachments: MutableList<ThumbnailImage>) {
        addMealThumbnailsView.showQueue(attachments)
    }

    override fun onInformationPassed(ingredient: Ingredient) {
        addIngredientDialog?.dismiss()
        presenter?.onInformationPassed(ingredient)
    }

    override fun onInformationPassed(imagePath: String) {
        galleryCameraDialog?.dismiss()
        presenter?.onInformationPassed(imagePath)
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

    override fun setupAdapter(ingredients: List<IngredientViewModel>) {
        adapter = AddedIngredientsAdapter(ingredients, this, this)

        ingredientListRv.adapter = adapter
    }

    override fun onImageDelete(image: ThumbnailImage) {
        presenter?.onImageDeleteClicked(image)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null && actionId == EditorInfo.IME_ACTION_NEXT) {
            preparationInput.requestFocus()

            return true
        }

        return false
    }

    override fun updateIngredients(ingredients: List<IngredientViewModel>) {
        adapter.fillAdapter(ingredients)
    }

    override fun enableImagesBtn(isEnabled: Boolean) {
        addImagesTv.isEnabled = isEnabled
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onItemClick(model: IngredientViewModel) {
        presenter?.onIngredientDeleteClicked(model)
    }

    override fun fieldChanged(model: IngredientViewModel, quantity: Double) {
        presenter?.onIngredientChanged(model, quantity)
    }

    companion object {
        private const val ADD_DIALOG = "add_dialog"
        private const val ADD_DIALOG_ID = 200

        private const val GALLERY_DIALOG = "gallery_dialog"
        private const val GALLERY_DIALOG_ID = 300
    }
}

