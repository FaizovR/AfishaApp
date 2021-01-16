package ru.faizovr.afisha.presentation.contract

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.faizovr.afisha.domain.model.EventShortInfo

interface EventListContract {

    interface View {
        fun setupView()
        fun setErrorTextVisibility(isVisible: Boolean)
        fun setProgressBarVisibility(isVisible: Boolean)
        fun setEventListVisibility(isVisible: Boolean)
        fun setRetryButtonVisibility(isVisible: Boolean)
        fun setupDataToList(events: Flow<PagingData<EventShortInfo>>)

        fun showNewFragment(eventShortInfo: EventShortInfo)
        fun onRetryClicked()
    }

    interface Presenter {
        fun onRetryButtonClicked()
        fun onLoadStateChanged(loadState: CombinedLoadStates)
        fun onEventClicked(eventShortInfo: EventShortInfo)
    }
}