package com.teammealky.mealky.presentation.shoppinglist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.shopping_list_fragment.*
import kotlinx.android.synthetic.main.shopping_toolbar.*
import kotlinx.android.synthetic.main.shopping_toolbar.view.*
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import com.teammealky.mealky.presentation.shoppinglist.component.addingredient.view.AddIngredientDialog
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
import kotlinx.android.synthetic.main.empty_item.*

class ShoppingListFragment : BaseFragment<ShoppingListPresenter, ShoppingListPresenter.UI, ShoppingListViewModel>(), ShoppingListPresenter.UI,
        ShoppingListAdapter.ShoppingListItemListener, View.OnClickListener,AddIngredientDialog.AddIngredientListener {

    override val vmClass = ShoppingListViewModel::class.java
    private lateinit var adapter: ShoppingListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var addIngredientDialog: AddIngredientDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)

        dialogRestoration(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.shopping_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        shoppingListToolbar.clearListBtn.setOnClickListener(this)
        shoppingListToolbar.plusBtn.setOnClickListener(this)
    }

    override fun setupRecyclerView(ingredients: List<ShoppingListItemViewModel>) {
        layoutManager = LinearLayoutManager(context)
        adapter = ShoppingListAdapter(ingredients, this)

        shopListRv.adapter = adapter
        shopListRv.setHasFixedSize(true)
        shopListRv.layoutManager = layoutManager
    }

    override fun onItemClick(model: ShoppingListItemViewModel) {
        presenter?.onItemClicked(model)
    }

    override fun showDialog() {
        AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
                .setTitle(R.string.are_you_sure)
                .setMessage(R.string.you_will_lose)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    presenter?.clearConfirmed()
                }
                .setNeutralButton(R.string.go_back) { _, _ -> }
                .setCancelable(false)
                .show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.clearListBtn -> presenter?.onClearListBtnClicked()
            R.id.plusBtn -> presenter?.onPlusBtnClicked()
        }
    }

    override fun showToast(succeeded: Boolean) {
        val message = if (succeeded) getString(R.string.list_cleared) else getString(R.string.something_went_wrong)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun fillList(ingredients: List<ShoppingListItemViewModel>) {
        adapter.fillAdapter(ingredients)
    }

    override fun clearList() {
        adapter.clearAdapter()
    }

    override fun enableClearListBtn(isEnabled: Boolean) {
        shoppingListToolbar.clearListBtn.isEnabled = isEnabled
    }

    override fun showEmptyView(isEnabled: Boolean) {
        emptyItemLayout.isVisible(isEnabled)
    }

    override fun fieldChanged(model: ShoppingListItemViewModel, quantity: Double) {
        presenter?.fieldChanged(model, quantity)
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

    override fun onInformationPassed(ingredient: Ingredient) {
        addIngredientDialog?.dismiss()
        presenter?.onInformationPassed(ingredient)
    }

    override fun showAddIngredientDialog(ingredients: List<Ingredient>) {
        addIngredientDialog = AddIngredientDialog.newInstance(ingredients)
        addIngredientDialog?.setTargetFragment(this, ADD_DIALOG_ID)
        addIngredientDialog?.show(fragmentManager, ADD_DIALOG)
    }

    companion object {
        private const val ADD_DIALOG = "info_dialog"
        private const val ADD_DIALOG_ID = 200
    }

}
