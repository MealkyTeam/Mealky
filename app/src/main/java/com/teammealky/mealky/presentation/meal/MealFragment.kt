package com.teammealky.mealky.presentation.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.meal.adapter.GalleryAdapter
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.meal.mapper.MealMapper
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import kotlinx.android.synthetic.main.meal_fragment.*
import timber.log.Timber

class MealFragment : BaseFragment<MealPresenter, MealPresenter.UI, MealViewModel>(), MealPresenter.UI,
        IngredientsAdapter.OnItemClickListener, View.OnClickListener {

    override val vmClass = MealViewModel::class.java
    private lateinit var adapter: IngredientsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)

        arguments?.let {
            presenter?.onCreated(MealMapper.readExtra(it))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.meal_fragment, container, false)

    override fun setupView(meal: Meal, ingredientsViewModels: List<IngredientViewModel>) {
        setupRecyclerView(ingredientsViewModels)
        setupImages(meal)
        setupTexts(meal)
        ingredientsBtn.setOnClickListener(this)
    }

    private fun setupRecyclerView(ingredientsViewModels: List<IngredientViewModel>) {
        layoutManager = LinearLayoutManager(context)
        adapter = IngredientsAdapter(ingredientsViewModels, this)

        ingredientListRv.adapter = adapter
        ingredientListRv.setHasFixedSize(true)
        ingredientListRv.layoutManager = layoutManager
    }

    private fun setupImages(meal: Meal) {
        val images = meal.images
        imagePager.adapter = GalleryAdapter(requireContext(), images)
    }

    private fun setupTexts(meal: Meal) {
        if (meal.author.username.isEmpty()) {
            author.isVisible(false)
        } else {
            author.text = meal.author.username
            author.isVisible(true)
        }
        mealName.text = meal.name
        prepTime.text = getString(R.string.prep_time, meal.prepTime.toString())
        preparation.text = meal.preparation
    }

    override fun onItemClick(model: IngredientViewModel) {
        presenter?.onIngredientClicked(model)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ingredientsBtn -> presenter?.onIngredientsButtonClicked()
        }
    }

    override fun showToast(succeeded: Boolean) {
        val message = if (succeeded) getString(R.string.save_ingredients_text) else getString(R.string.something_went_wrong)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(meal: Meal): MealFragment {
            val fragment = MealFragment()
            fragment.arguments = MealMapper.writeExtra(meal)

            return fragment
        }
    }
}
