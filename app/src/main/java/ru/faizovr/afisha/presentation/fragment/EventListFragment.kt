package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.activity.MainActivity
import ru.faizovr.afisha.presentation.adapter.EventListAdapter
import ru.faizovr.afisha.presentation.adapter.FooterAdapter
import ru.faizovr.afisha.presentation.contract.EventListContract
import ru.faizovr.afisha.presentation.presenter.EventListPresenter

class EventListFragment(private var category: Category?) : Fragment(R.layout.fragment_event_list),
    EventListContract.View {

    private var presenter: EventListContract.Presenter? = null

    constructor() : this(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val parcelable = savedInstanceState.getParcelable<Category>(CATEGORY_KEY)
            if (parcelable != null) {
                category = parcelable
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupPresenter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(CATEGORY_KEY, category)
    }

    override fun setupView() {
        button_event_list_retry.setOnClickListener {
            presenter?.onRetryButtonClicked()
        }
    }

    override fun setupList(eventListAdapter: EventListAdapter) {
        eventListAdapter.addLoadStateListener { loadState -> presenter?.onLoadStateChanged(loadState) }
        recycler_view_event_list.apply {
            setHasFixedSize(true)
            adapter = eventListAdapter.withLoadStateFooter(
                footer = FooterAdapter { eventListAdapter.retry() }
            )
        }
    }

    private fun setupPresenter() {
        val app: App = activity?.application as App
        presenter = category?.let { EventListPresenter(this, app.repository, it) }
        presenter?.init()
    }

    override fun setRetryButtonVisibility(isVisible: Boolean) {
        button_event_list_retry.isVisible = isVisible
    }

    override fun setErrorTextVisibility(isVisible: Boolean) {
        text_view_event_list_failed_message.isVisible = isVisible
    }

    override fun setProgressBarVisibility(isVisible: Boolean) {
        progress_bar_event_list.isVisible = isVisible
    }

    override fun setEventListVisibility(isVisible: Boolean) {
        recycler_view_event_list.isVisible = isVisible
    }

    override fun setupLiveData(
        events: Flow<PagingData<EventShortInfo>>,
        eventListAdapter: EventListAdapter,
    ) {
        lifecycleScope.launch {
            events.collectLatest { eventListAdapter.submitData(it) }
        }
    }

    companion object {
        private const val CATEGORY_KEY = "Category"
    }
}