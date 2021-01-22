package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.databinding.FragmentEventListBinding
import ru.faizovr.afisha.presentation.activity.MainActivity
import ru.faizovr.afisha.presentation.adapter.EventListAdapter
import ru.faizovr.afisha.presentation.adapter.FooterAdapter
import ru.faizovr.afisha.presentation.model.EventListDataView
import ru.faizovr.afisha.presentation.viewmodel.EventListViewModel
import ru.faizovr.afisha.presentation.viewmodel.EventListViewModelFactory

class EventListFragment : Fragment(R.layout.fragment_event_list) {

    private var onEventClicked: (eventListDataView: EventListDataView) -> Unit =
        { eventListDataView ->
            showNewFragment(eventListDataView)
        }

    private val categoryTag: String by lazy {
        requireArguments().getString(
            EVENT_LIST_CATEGORY_TAG_KEY,
            ""
        )
    }

    private val viewModel: EventListViewModel by viewModels {
        val repository = (requireActivity().application as App).repository
        EventListViewModelFactory(repository, categoryTag)
    }

    private val binding by viewBinding(FragmentEventListBinding::bind)
    private var eventListAdapter: EventListAdapter? = null
    private val loadStateListener: (CombinedLoadStates) -> Unit =
        { loadState: CombinedLoadStates ->
            viewModel.onLoadStateChanged(
                loadState
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        setupList()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.buttonRetryVisibility.observe(
            viewLifecycleOwner,
            this@EventListFragment::setRetryButtonVisibility
        )
        viewModel.eventListVisibility.observe(
            viewLifecycleOwner,
            this@EventListFragment::setEventListVisibility
        )
        viewModel.errorTextVisibility.observe(
            viewLifecycleOwner,
            this@EventListFragment::setErrorTextVisibility
        )
        viewModel.progressBarVisibility.observe(
            viewLifecycleOwner,
            this@EventListFragment::setProgressBarVisibility
        )
        lifecycleScope.launch {
            val eventListAdapter1 = eventListAdapter
            if (eventListAdapter1 != null) {
                viewModel.listData.collectLatest(eventListAdapter1::submitData)
            }
        }
    }

    private fun setupToolbar() {
        val title: String = arguments?.getString(EVENT_LIST_CATEGORY_TITLE_KEY) ?: ""
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    private fun setupView() {
        binding.buttonEventListRetry.setOnClickListener {
            eventListAdapter?.retry()
        }
    }

    private fun setupList() {
        eventListAdapter = EventListAdapter(onEventClicked)
        with(binding) {
            recyclerViewEventList.apply {
                setHasFixedSize(true)
                adapter = eventListAdapter?.withLoadStateFooter(
                    footer = FooterAdapter { eventListAdapter?.retry() }
                )
            }
        }
        eventListAdapter?.addLoadStateListener(loadStateListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventListAdapter?.removeLoadStateListener(loadStateListener)
    }

    private fun setRetryButtonVisibility(isVisible: Boolean) {
        binding.buttonEventListRetry.isVisible = isVisible
    }

    private fun setErrorTextVisibility(isVisible: Boolean) {
        binding.textViewEventListFailedMessage.isVisible = isVisible
    }

    private fun setProgressBarVisibility(isVisible: Boolean) {
        binding.progressBarEventList.isVisible = isVisible
    }

    private fun setEventListVisibility(isVisible: Boolean) {
        binding.recyclerViewEventList.isVisible = isVisible
    }

    private fun showNewFragment(eventListDataView: EventListDataView) {
        val fragment: Fragment =
            EventDetailFragment.newInstance(eventListDataView.id, eventListDataView.title)
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    companion object {
        private const val EVENT_LIST_CATEGORY_TAG_KEY = "Event_List_Category_tag"
        private const val EVENT_LIST_CATEGORY_TITLE_KEY = "Event_List_Category_Title"
        fun newInstance(categoryTag: String, categoryTitle: String): EventListFragment {
            val args = Bundle()
            args.putString(EVENT_LIST_CATEGORY_TAG_KEY, categoryTag)
            args.putString(EVENT_LIST_CATEGORY_TITLE_KEY, categoryTitle)
            Log.d("TAG", "newInstance: $categoryTag $categoryTitle ")
            val fragment = EventListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}