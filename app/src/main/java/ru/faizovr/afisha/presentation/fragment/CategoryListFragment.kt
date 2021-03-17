package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import ru.faizovr.afisha.R
import ru.faizovr.afisha.core.presentation.fragment.RefreshableFragment
import ru.faizovr.afisha.databinding.FragmentCategoryListBinding
import ru.faizovr.afisha.presentation.adapter.CategoryAdapter
import ru.faizovr.afisha.presentation.commands.CategoriesCommands
import ru.faizovr.afisha.presentation.model.CategoriesListScreenState
import ru.faizovr.afisha.presentation.model.CategoryDataView
import ru.faizovr.afisha.presentation.viewmodel.CategoryListViewModel

@AndroidEntryPoint
class CategoryListFragment :
    RefreshableFragment<CategoriesListScreenState, CategoriesCommands, CategoryListViewModel>(
        R.layout.fragment_category_list,
        CategoryListViewModel::class.java
    ) {

    private val binding by viewBinding(FragmentCategoryListBinding::bind)
    private var categoriesListAdapter: CategoryAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupAppBar(binding.abCategoryList.actionBar)
        setupView()
    }

    private fun setupAppBar(toolBar: MaterialToolbar) {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolBar)
        toolBar.setNavigationOnClickListener { viewModel.onNavigationBackClicked() }
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.category_title)
        }
    }

    private fun setupView() {
        categoriesListAdapter = CategoryAdapter(viewModel::onCategoriesListItemClicked)
        binding.recyclerViewCategory.adapter = categoriesListAdapter
    }

    override fun renderView(model: CategoriesListScreenState) {
        super.renderView(model)
        setCategoryListVisibility(model.isCategoryListVisible)
        updateCategoriesList(model.categories)
    }

    override fun executeCommand(command: CategoriesCommands) =
        when (command) {
            is CategoriesCommands.OpenEventList -> openEventList(command.categoryDataView)
            is CategoriesCommands.NavigateToPreviousScreen -> navigateToPreviousScreen()
        }

    private fun openEventList(categoryDataView: CategoryDataView) {
        val args = EventListFragmentArgs(categoryDataView.tag, categoryDataView.name).toBundle()
        navController.navigate(R.id.navigation_event_list, args)
    }

    private fun setCategoryListVisibility(isVisible: Boolean) {
        binding.recyclerViewCategory.isVisible = isVisible
    }

    private fun updateCategoriesList(categories: List<CategoryDataView>?) =
        categoriesListAdapter?.submitList(categories)
}