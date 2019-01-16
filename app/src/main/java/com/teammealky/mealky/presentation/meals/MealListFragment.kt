package com.teammealky.mealky.presentation.meals

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.listener.PaginationScrollListener
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.meals.MealListPresenter.Companion.LIMIT
import com.teammealky.mealky.presentation.meals.adapter.MealsAdapter
import kotlinx.android.synthetic.main.meals_fragment.*
import kotlinx.android.synthetic.main.search_toolbar.*
import timber.log.Timber

class MealListFragment : BaseFragment<MealListPresenter, MealListPresenter.UI, MealListViewModel>(),
        MealListPresenter.UI, MealsAdapter.OnItemClickListener,
        TextWatcher, TextView.OnEditorActionListener {

    override val vmClass = MealListViewModel::class.java

    private lateinit var adapter: MealsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.meals_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearch()
        setupRecyclerView()
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(this)
        searchEditText.setOnEditorActionListener(this)
    }

    override fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        adapter = MealsAdapter(listener = this)

        mealListRv.adapter = adapter
        mealListRv.setHasFixedSize(true)
        mealListRv.layoutManager = layoutManager

        mealListRv.addOnScrollListener(object : PaginationScrollListener(layoutManager as LinearLayoutManager, LIMIT - 2) {
            override fun isLastPage(): Boolean {
                return presenter?.isLastPage() ?: true
            }

            override fun isLoading(): Boolean {
                return presenter?.isLoading ?: false
            }

            override fun loadMoreItems() {
                presenter?.isLoading = true
                presenter?.loadMore()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                presenter?.scrolled(newState)
            }
        })
    }

    override fun fillList(meals: List<Meal>) {
        adapter.addItems(meals)
    }

    override fun clearList() {
        adapter.meals = emptyList()
    }

    override fun setVisibleItem(visibleItemId: Int) {
        layoutManager.scrollToPosition(visibleItemId)
    }

    override fun isLoading(isLoading: Boolean) {
        progressBar.isVisible(isLoading)
    }

    override fun openItem(meal: Meal) {
        Navigator.from(context as Navigator.Navigable).openMeal(meal)
    }

    override fun onItemClick(item: Meal) {
        presenter?.onItemClicked(item)
    }

    override fun scrollToTop() {
        mealListRv.scrollToPosition(0)
    }

    override fun onPause() {
        val itemPosition = (mealListRv.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        presenter?.onPaused(itemPosition)

        super.onPause()
    }

    override fun afterTextChanged(editable: Editable?) {
        if (presenter?.currentQuery == searchEditText.text.toString()) {
            Timber.d("KUBA_LOG Method:afterTextChanged ***** ${presenter?.currentQuery} *****")
            return
        }
        presenter?.currentQuery = searchEditText.text.toString()
        presenter?.search()
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null && actionId == EditorInfo.IME_ACTION_DONE) {
            presenter?.search()
            return true
        }

        return false
    }
}
