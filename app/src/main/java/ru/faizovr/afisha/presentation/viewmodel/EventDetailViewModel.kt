package ru.faizovr.afisha.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getFetchScreenData(allowCachedResult: Boolean): Flow<Lce<EventDetailDataView>>? =
        model.eventId?.let { eventId ->
            eventsInteractor.getEventById(eventId).map { lce ->
                lce.toLceWithTransformedContent(eventMapper::map)
            }
        }

    override fun getUpdatedModelForLce(lce: Lce<EventDetailDataView>?): EventDetailScreenState {
        updateScreenState(lce = lce)
        return model
    }

    fun onNavigationBackClicked() =
        executeCommand(EventDetailCommands.NavigateToPreviousScreen)
}