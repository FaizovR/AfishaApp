package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.databinding.FragmentCategoryListBinding
import ru.faizovr.afisha.presentation.activity.MainActivity
import ru.faizovr.afisha.presentation.adapter.CategoryAdapter
import ru.faizovr.afisha.presentation.commands.CategoriesCommands
import ru.faizovr.afisha.presentation.model.CategoryDataView
import ru.faizovr.afisha.presentation.viewmodel.CategoryListViewModel
import ru.faizovr.afisha.presentation.viewmodel.CategoryListViewModelFactory

class CategoryListFragment : Fragment(R.layout.fragment_category_list) {

    private val viewModel: CategoryListViewModel by viewModels {
        val repository = (requireActivity().application as App).repository
        CategoryListViewModelFactory(repository)
    }
    private val binding by viewBinding(FragmentCategoryListBinding::bind)
    private var categoriesListAdapter: CategoryAdapter? = null
    private val onMenuClicked: (categoryDataView: CategoryDataView) -> Unit = {
        viewModel.onCategoriesListItemClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        setupObservers()
    }

    private fun executeCommand(command: CategoriesCommands) {
        when (command) {
            is CategoriesCommands.OpenEventList -> {
                showNewFragment(command.categoryDataView)
            }
        }
    }

    private fun setupObservers() {
        viewModel.commandsEvent.observe(viewLifecycleOwner, this::executeCommand)
        viewModel.buttonRetryVisibility.observe(
            viewLifecycleOwner,
            this@CategoryListFragment::setRetryButtonVisibility
        )
        viewModel.categoriesListVisibility.observe(
            viewLifecycleOwner,
            this@CategoryListFragment::setCategoryListVisibility
        )
        viewModel.errorTextVisibility.observe(
            viewLifecycleOwner,
            this@CategoryListFragment::setErrorTextVisibility
        )
        viewModel.progressBarVisibility.observe(
            viewLifecycleOwner,
            this@CategoryListFragment::setProgressBarVisibility
        )
        viewModel.categoriesDataViewList.observe(viewLifecycleOwner) {
            categoriesListAdapter?.updateList(it)
        }
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.category_title)
        }
    }

    private fun setupView() {
        categoriesListAdapter = CategoryAdapter(onMenuClicked)
        binding.recyclerViewCategory.adapter = categoriesListAdapter
        binding.buttonMenuRetry.setOnClickListener {
            viewModel.onRetryClicked()
        }
    }

    private fun showNewFragment(categoryDataView: CategoryDataView) {
        val fragment: Fragment =
            EventListFragment.newInstance(categoryDataView.tag, categoryDataView.name)
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    private fun setRetryButtonVisibility(isVisible: Boolean) {
        binding.buttonMenuRetry.isVisible = isVisible
    }

    private fun setCategoryListVisibility(isVisible: Boolean) {
        binding.recyclerViewCategory.isVisible = isVisible
    }

    private fun setErrorTextVisibility(isVisible: Boolean) {
        binding.textViewCategoryFailedMessage.isVisible = isVisible
    }

    private fun setProgressBarVisibility(isVisible: Boolean) {
        binding.progressBarCategory.isVisible = isVisible
    }

    companion object {
        fun newInstance(): CategoryListFragment {
            val args = Bundle()

            val fragment = CategoryListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}