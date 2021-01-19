package ru.faizovr.afisha.presentation.contract

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.faizovr.afisha.presentation.model.EventListDataView

interface EventListContract {

    interface View {
        fun setupView()
        fun setErrorTextVisibility(isVisible: Boolean)
        fun setProgressBarVisibility(isVisible: Boolean)
        fun setEventListVisibility(isVisible: Boolean)
        fun setRetryButtonVisibility(isVisible: Boolean)
        fun setupDataToList(events: Flow<PagingData<EventListDataView>>)
        fun showNewFragment(eventListDataView: EventListDataView)
        fun onRetryClicked()
    }

    interface Presenter {
        fun init()
        fun onRetryButtonClicked()
        fun onLoadStateChanged(loadState: CombinedLoadStates)
        fun onEventClicked(eventListDataView: EventListDataView)
    }
}