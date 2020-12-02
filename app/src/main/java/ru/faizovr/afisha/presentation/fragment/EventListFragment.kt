package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import kotlinx.android.synthetic.main.fragment_event_list.*
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.adapter.EventListAdapter
import ru.faizovr.afisha.presentation.contract.EventListContract
import ru.faizovr.afisha.presentation.presenter.EventListPresenter

class EventListFragment(private val category: Category) : Fragment(R.layout.fragment_event_list),
    EventListContract.View {

    private var presenter: EventListContract.Presenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupPresenter()
    }

    private fun setupViews() {
        val eventListAdapter = EventListAdapter()
        recycler_view_event_list.adapter = eventListAdapter
    }

    private fun setupPresenter() {
        val app: App = activity?.application as App
        presenter = EventListPresenter(this, app.repository, category)
        presenter?.init()
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

    override fun setupLiveData(events: LiveData<PagedList<EventShortInfo>>) {
        val eventListAdapter = recycler_view_event_list.adapter as EventListAdapter
        events.observe(this, Observer(eventListAdapter::submitList))
    }
}