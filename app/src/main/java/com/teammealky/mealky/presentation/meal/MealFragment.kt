package com.teammealky.mealky.presentation.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.meal.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.meal_fragment.*

class MealFragment : BaseFragment<MealPresenter, MealPresenter.UI, MealViewModel>(), MealPresenter.UI {

    override val vmClass = MealViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)

        arguments?.let {
            presenter?.meal = MealMapper.readExtra(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.meal_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        mealName.text = presenter?.meal?.name
        prepTime.text = getString(R.string.prep_time, presenter?.meal?.prepTime.toString())
        preparation.text = presenter?.meal?.preparation

        val images = presenter?.meal?.images ?: emptyList()
        imagePager.adapter = GalleryAdapter(requireContext(), images)
    }

    companion object {
        fun newInstance(meal: Meal): MealFragment {
            val fragment = MealFragment()
            fragment.arguments = MealMapper.writeExtra(meal)

            return fragment
        }
    }
}
