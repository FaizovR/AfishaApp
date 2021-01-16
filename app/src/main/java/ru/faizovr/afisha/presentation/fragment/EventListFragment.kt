package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.databinding.FragmentEventListBinding
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.activity.MainActivity
import ru.faizovr.afisha.presentation.adapter.EventListAdapter
import ru.faizovr.afisha.presentation.adapter.FooterAdapter
import ru.faizovr.afisha.presentation.contract.EventListContract
import ru.faizovr.afisha.presentation.presenter.EventListPresenter

class EventListFragment : Fragment(R.layout.fragment_event_list),
    EventListContract.View {

    private var onEventClicked: (eventShortInfo: EventShortInfo) -> Unit = { eventShortInfo ->
        presenter?.onEventClicked(eventShortInfo)
    }
    private var presenter: EventListContract.Presenter? = null
    private lateinit var binding: FragmentEventListBinding
    private var eventListAdapter: EventListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupList()
        setupPresenter()
    }

    private fun setupToolbar() {
        val title: String = arguments?.getString(EVENT_LIST_CATEGORY_TITLE_KEY) ?: ""
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    override fun setupView() {
        binding.buttonEventListRetry.setOnClickListener {
            presenter?.onRetryButtonClicked()
        }
    }

    private fun setupList() {
        eventListAdapter = EventListAdapter(onEventClicked)
        eventListAdapter?.addLoadStateListener { loadState ->
            presenter?.onLoadStateChanged(
                loadState
            )
        }
        binding.recyclerViewEventList.apply {
            setHasFixedSize(true)
            adapter = eventListAdapter?.withLoadStateFooter(
                footer = FooterAdapter { eventListAdapter?.retry() }
            )
        }
    }

    private fun setupPresenter() {
        val app: App = requireActivity().application as App
        val categoryTag = arguments?.getString(EVENT_LIST_CATEGORY_TAG_KEY)
        if (categoryTag != null) {
            presenter = EventListPresenter(this, app.repository, categoryTag)
        }
    }

    override fun setRetryButtonVisibility(isVisible: Boolean) {
        binding.buttonEventListRetry.isVisible = isVisible
    }

    override fun setErrorTextVisibility(isVisible: Boolean) {
        binding.textViewEventListFailedMessage.isVisible = isVisible
    }

    override fun setProgressBarVisibility(isVisible: Boolean) {
        binding.progressBarEventList.isVisible = isVisible
    }

    override fun setEventListVisibility(isVisible: Boolean) {
        binding.recyclerViewEventList.isVisible = isVisible
    }

    override fun onRetryClicked() {
        eventListAdapter?.retry()
    }

    override fun setupDataToList(
        events: Flow<PagingData<EventShortInfo>>
    ) {
        lifecycleScope.launch {
            val eventListAdapter1 = eventListAdapter
            if (eventListAdapter1 != null) {
                events.collectLatest(eventListAdapter1::submitData)
            }
        }
    }

    override fun showNewFragment(eventShortInfo: EventShortInfo) {
        val fragment: Fragment =
            EventDetailFragment.newInstance(eventShortInfo.id, eventShortInfo.title)
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    companion object {
        private const val EVENT_LIST_CATEGORY_TAG_KEY = "Event_List_Category_tag"
        private const val EVENT_LIST_CATEGORY_TITLE_KEY = "Event_List_Category_Title"
        fun newInstance(categoryTag: String, categoryTitle: String): EventListFragment {
            val args = Bundle()
            args.putString(EVENT_LIST_CATEGORY_TAG_KEY, categoryTag)
            args.putString(EVENT_LIST_CATEGORY_TITLE_KEY, categoryTitle)
            val fragment = EventListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}