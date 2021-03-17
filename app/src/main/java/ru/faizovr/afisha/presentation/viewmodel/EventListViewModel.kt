package ru.faizovr.afisha.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.core.presentation.viewModel.ScreenDataFetchingViewModel
import ru.faizovr.afisha.domain.interactors.EventsInteractor
import ru.faizovr.afisha.presentation.commands.EventListCommands
import ru.faizovr.afisha.presentation.datasource.EventListDataSource
import ru.faizovr.afisha.presentation.mapper.EventListDataViewMapper
import ru.faizovr.afisha.presentation.model.EventListDataView
import ru.faizovr.afisha.presentation.model.EventListScreenState
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventListInteractor: EventsInteractor,
    private val eventListDataViewMapper: EventListDataViewMapper
) : ScreenDataFetchingViewModel<Nothing, EventListScreenState, EventListCommands>(
    EventListScreenState()
) {

    lateinit var listData: Flow<PagingData<EventListDataView>>

    override fun init(id: String?) {
        id?.let {
            listData = Pager(
                PagingConfig(pageSize = 20)
            ) {
                EventListDataSource(eventListInteractor, id)
            }.flow
                .map { pagingData ->
                    withContext(Dispatchers.Default) {
                        pagingData.map {
                            eventListDataViewMapper.mapFromEntity(it)
                        }
                    }
                }
                .cachedIn(viewModelScope)
        }
    }

    fun updateScreenState(
        lce: Lce<Nothing>? = model.lce,
        shouldRefreshView: Boolean = true
    ) {
        model = EventListScreenState(lce = lce)
        if (shouldRefreshView) {
            refreshView()
        }
    }

    fun onEventListItemClicked(eventListDataView: EventListDataView) =
        executeCommand(EventListCommands.OpenEventDetail(eventListDataView))

    fun onLoadStateChanged(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                updateScreenState(Lce.loading())
            }
            is LoadState.Error -> {
                updateScreenState(Lce.error((loadState.refresh as LoadState.Error).error))
            }
            else -> {
                updateScreenState(Lce.data(null))
            }
        }
    }

    override suspend fun getFetchScreenData(allowCachedResult: Boolean): Flow<Lce<Nothing>>? {
        return null
    }

    override fun getUpdatedModelForLce(lce: Lce<Nothing>?): EventListScreenState {
        return model
    }

    fun onNavigationBackClicked() =
        executeCommand(EventListCommands.NavigateToPreviousScreen)
}