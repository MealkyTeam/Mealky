package com.teammealky.mealky.presentation.addmeal

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View.OnClickListener
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import kotlinx.android.synthetic.main.activity_add_meal.*
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult
import com.teammealky.mealky.presentation.commons.extension.isInvisible
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.presentation.addmeal.adapter.AddedIngredientsAdapter
import com.teammealky.mealky.presentation.addmeal.gallerycameradialog.GalleryCameraDialog.GalleryCameraListener
import com.teammealky.mealky.presentation.addmeal.model.ThumbnailImage
import com.teammealky.mealky.presentation.addmeal.view.AddMealThumbnailsView.OnImageDeleteListener
import com.teammealky.mealky.presentation.commons.component.addingredient.AddIngredientDialog.AddIngredientListener
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter.OnItemClickListener
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import com.teammealky.mealky.presentation.commons.view.IngredientQuantityView.Companion.FieldChangedListener
import android.content.Intent
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.teammealky.mealky.presentation.addmeal.gallerycameradialog.GalleryCameraDialog
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.component.addingredient.AddIngredientDialog
import com.teammealky.mealky.presentation.commons.presenter.BaseActivity
import timber.log.Timber
import java.io.File


class AddMealActivity : BaseActivity<AddMealPresenter, AddMealPresenter.UI, AddMealViewModel>(),
        AddMealPresenter.UI, OnClickListener, TextWatcher, OnEditorActionListener, AddIngredientListener,
        GalleryCameraListener, OnImageDeleteListener, Navigator.Navigable, FieldChangedListener, OnItemClickListener {

    override val vmClass = AddMealViewModel::class.java
    private var addIngredientDialog: AddIngredientDialog? = null
    private var galleryCameraDialog: GalleryCameraDialog? = null
    private lateinit var adapter: AddedIngredientsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(this).getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meal)

        dialogRestoration(savedInstanceState)
        initUI()
    }

   private fun initUI(){
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
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
        layoutManager = LinearLayoutManager(this)
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

    override fun toMainActivity() {
        Navigator.from(this as Navigator.Navigable).openActivity(Navigator.ACTIVITY_MAIN)
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
        Toast.makeText(this, getString(R.string.meal_added), Toast.LENGTH_LONG).show()
    }

    override fun isLoading(isLoading: Boolean) {
        addMealLayout.isInvisible(isLoading)
        addMealProgressBar.isVisible(isLoading)
    }

    override fun showAddIngredientDialog(ingredients: List<Ingredient>) {
        addIngredientDialog = AddIngredientDialog.newInstance(ingredients)
        addIngredientDialog?.show(supportFragmentManager, ADD_DIALOG)
    }

    override fun showGalleryCameraDialog() {
        galleryCameraDialog = GalleryCameraDialog()
        galleryCameraDialog?.show(supportFragmentManager, GALLERY_DIALOG)
    }

    private fun dialogRestoration(savedInstanceState: Bundle?) {
        if (null != savedInstanceState) {
            val prevDialog = supportFragmentManager.findFragmentByTag(ADD_DIALOG)
            if (prevDialog is AddIngredientDialog) {
                addIngredientDialog = prevDialog
            }
        }
    }

    override fun showImagesQueue(attachments: MutableList<ThumbnailImage>) {
        addMealThumbnailsView.showQueue(attachments)
    }

    override fun onInformationPassed(ingredient: Ingredient) {
        presenter?.onInformationPassed(ingredient)
    }

    override fun onInformationPassed(file: File) {
        presenter?.onInformationPassed(file)
    }

    override fun afterTextChanged(editable: Editable?) {
        presenter?.fieldsChanged(titleInput.text?.toString(), preparationTimeInput.text?.toString(), preparationInput.text?.toString())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.confirmBtn -> presenter?.onConfirmBtnClicked()
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

    override fun onBackPressed(){
        val canGoBack = presenter?.canGoBack ?: false

        if (canGoBack)
            super.onBackPressed()
        else
            presenter?.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        galleryCameraDialog?.onActivityResult(requestCode,resultCode,data)
    }

    override fun showGoBackDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.be_careful))
        builder.setMessage(getString(R.string.add_meal_exit_message))
        builder.setPositiveButton(getString(R.string.confirm)) { _, _ ->
            presenter?.goBackConfirmed()
        }
        builder.setNeutralButton(getString(R.string.cancel)) { _, _ ->

        }
        builder.show()
    }

    override fun getContext() = this

    override fun navigateTo(fragment: Fragment, cleanStack: Boolean) {
        Timber.tag("KUBA").v("navigateTo NOT IMPLEMENTED")
    }
    override fun forceGoBack() {
        onBackPressed()
    }

    companion object {
        private const val ADD_DIALOG = "add_dialog"
        private const val ADD_DIALOG_ID = 200

        private const val GALLERY_DIALOG = "gallery_dialog"
        private const val GALLERY_DIALOG_ID = 300
    }
}

