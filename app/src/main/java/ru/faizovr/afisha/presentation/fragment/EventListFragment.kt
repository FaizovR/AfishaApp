package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.faizovr.afisha.R
import ru.faizovr.afisha.core.presentation.fragment.RefreshableFragment
import ru.faizovr.afisha.databinding.FragmentEventListBinding
import ru.faizovr.afisha.presentation.adapter.EventListAdapter
import ru.faizovr.afisha.presentation.adapter.FooterAdapter
import ru.faizovr.afisha.presentation.commands.EventListCommands
import ru.faizovr.afisha.presentation.model.EventListDataView
import ru.faizovr.afisha.presentation.model.EventListScreenState
import ru.faizovr.afisha.presentation.viewmodel.EventListViewModel

@AndroidEntryPoint
class EventListFragment :
    RefreshableFragment<EventListScreenState, EventListCommands, EventListViewModel>(
        R.layout.fragment_event_list,
        EventListViewModel::class.java
    ) {

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
        val arguments = EventListFragmentArgs.fromBundle(requireArguments())
        viewModel.init(arguments.tag)
//        setupView()
        setupAppBar(binding.abEventList.actionBar)
        setupToolbar(arguments.title)
        setupList()
        setupObservers()
    }

    private fun setupAppBar(toolBar: MaterialToolbar) {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolBar)
        toolBar.setNavigationOnClickListener { viewModel.onNavigationBackClicked() }
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventListAdapter?.removeLoadStateListener(loadStateListener)
    }

    override fun executeCommand(command: EventListCommands) =
        when (command) {
            is EventListCommands.OpenEventDetail -> openEventDetail(command.eventListDataView)
            is EventListCommands.NavigateToPreviousScreen -> navigateToPreviousScreen()
        }

    // TODO: 03.03.2021 исправить
    private fun setupObservers() {
        lifecycleScope.launch {
            val eventListAdapter1 = eventListAdapter
            if (eventListAdapter1 != null) {
                viewModel.listData.collectLatest(eventListAdapter1::submitData)
            }
        }
    }

    private fun setupToolbar(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            this.title = title
        }
    }

    // TODO: 03.03.2021 error handling
//    private fun setupView() {
//        binding.buttonEventListRetry.setOnClickListener {
//            eventListAdapter?.retry()
//        }
//    }

    private fun setupList() {
        eventListAdapter = EventListAdapter(viewModel::onEventListItemClicked)
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

    private fun setEventListVisibility(isVisible: Boolean) {
        binding.recyclerViewEventList.isVisible = isVisible
    }

    private fun openEventDetail(eventListDataView: EventListDataView) {
        val args = EventDetailFragmentArgs(eventListDataView.id, eventListDataView.title).toBundle()
        navController.navigate(R.id.navigation_event_detail, args)
    }

}