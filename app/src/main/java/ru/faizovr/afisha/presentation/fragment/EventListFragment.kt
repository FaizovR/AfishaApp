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
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.adapter.EventListAdapter
import ru.faizovr.afisha.presentation.adapter.FooterAdapter
import ru.faizovr.afisha.presentation.contract.EventListContract
import ru.faizovr.afisha.presentation.presenter.EventListPresenter

class EventListFragment : Fragment(R.layout.fragment_event_list),
    EventListContract.View {

    private var presenter: EventListContract.Presenter? = null
    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupPresenter()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupView() {
        binding.buttonEventListRetry.setOnClickListener {
            presenter?.onRetryButtonClicked()
        }
    }

    override fun setupList(eventListAdapter: EventListAdapter) {
        eventListAdapter.addLoadStateListener { loadState -> presenter?.onLoadStateChanged(loadState) }
        binding.recyclerViewEventList.apply {
            setHasFixedSize(true)
            adapter = eventListAdapter.withLoadStateFooter(
                footer = FooterAdapter { eventListAdapter.retry() }
            )
        }
    }

    private fun setupPresenter() {
        val app: App = requireActivity().application as App
        val category = arguments?.getParcelable<Category>(EVENT_LIST_CATEGORY_KEY)
        if (category != null) {
            presenter = EventListPresenter(this, app.repository, category)
        }
        presenter?.init()
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

    override fun setupDataToList(
        events: Flow<PagingData<EventShortInfo>>,
        eventListAdapter: EventListAdapter,
    ) {
        lifecycleScope.launch {
            events.collectLatest(eventListAdapter::submitData)
        }
    }

    companion object {
        private const val EVENT_LIST_CATEGORY_KEY = "Event_Category"
        fun newInstance(category: Category): EventListFragment {
            val args = Bundle()
            args.putParcelable(EVENT_LIST_CATEGORY_KEY, category)
            val fragment = EventListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}