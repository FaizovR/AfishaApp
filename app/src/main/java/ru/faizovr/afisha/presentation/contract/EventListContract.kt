package ru.faizovr.afisha.presentation.contract

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.adapter.EventListAdapter

interface EventListContract {

    interface View {
        fun setupList(eventListAdapter: EventListAdapter)
        fun setupView()
        fun setErrorTextVisibility(isVisible: Boolean)
        fun setProgressBarVisibility(isVisible: Boolean)
        fun setEventListVisibility(isVisible: Boolean)
        fun setRetryButtonVisibility(isVisible: Boolean)
        fun setupLiveData(
            events: Flow<PagingData<EventShortInfo>>,
            eventListAdapter: EventListAdapter,
        )
    }

    interface Presenter {
        fun init()
        fun onRetryButtonClicked()
        fun onLoadStateChanged(loadState: CombinedLoadStates)
    }
}