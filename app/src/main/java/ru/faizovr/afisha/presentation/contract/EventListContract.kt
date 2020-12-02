package ru.faizovr.afisha.presentation.contract

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ru.faizovr.afisha.domain.model.EventShortInfo

interface EventListContract {

    interface View {
        fun setErrorTextVisibility(isVisible: Boolean)
        fun setProgressBarVisibility(isVisible: Boolean)
        fun setEventListVisibility(isVisible: Boolean)
        fun setupLiveData(events: LiveData<PagedList<EventShortInfo>>)
    }

    interface Presenter {
        fun init()
    }
}