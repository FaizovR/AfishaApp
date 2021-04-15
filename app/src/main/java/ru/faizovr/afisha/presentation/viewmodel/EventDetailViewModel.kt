package ru.faizovr.afisha.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.core.presentation.viewModel.ScreenDataFetchingViewModel
import ru.faizovr.afisha.domain.interactors.EventsInteractor
import ru.faizovr.afisha.presentation.commands.EventDetailCommands
import ru.faizovr.afisha.presentation.mapper.EventMapper
import ru.faizovr.afisha.presentation.model.EventDetailDataView
import ru.faizovr.afisha.presentation.model.EventDetailScreenState
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventsInteractor: EventsInteractor,
    private val eventMapper: EventMapper,
) : ScreenDataFetchingViewModel<EventDetailDataView, EventDetailScreenState, EventDetailCommands>(
    EventDetailScreenState()
) {

    fun init(eventId: Long) {
        updateScreenState(eventId = eventId)
        fetchScreenData()
    }

    private fun updateScreenState(
        lce: Lce<EventDetailDataView>? = model.lce,
        eventId: Long? = model.eventId,
        shouldRefreshView: Boolean = true
    ) {
        model = EventDetailScreenState(lce, eventId)
        if (shouldRefreshView)
            refreshView()
    }

    override suspend fun getFetchScreenData(allowCachedResult: Boolean): Lce<EventDetailDataView>? =
        model.eventId?.let { eventId ->
            val lce = eventsInteractor.getEventById(eventId)
            return lce.toLceWithTransformedContent(eventMapper::map)
        }

    override fun getUpdatedModelForLce(lce: Lce<EventDetailDataView>?): EventDetailScreenState {
        updateScreenState(lce = lce)
        return model
    }

    fun onNavigationBackClicked() =
        executeCommand(EventDetailCommands.NavigateToPreviousScreen)
}