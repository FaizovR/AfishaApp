package ru.faizovr.afisha.core.presentation.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.core.presentation.models.RefreshableScreenState
import ru.faizovr.afisha.core.presentation.navigation.Command

abstract class ScreenDataFetchingViewModel<
        ScreenDataType,
        ScreenState : RefreshableScreenState<ScreenDataType>,
        SupportedCommandType : Command>
    (
    screenState: ScreenState
) : BaseRefreshableViewModel<ScreenState, SupportedCommandType>(screenState) {

    open fun init(id: String? = null) {
        fetchScreenData()
    }

    protected open fun fetchScreenData(allowCachedResult: Boolean = false) {
        viewModelScope.launch {
            refreshViewForLce(Lce.loading())
            getFetchScreenData(allowCachedResult)
                ?.collect {
                    refreshViewForLce(it)
                }
        }
    }

    private fun refreshViewForLce(lce: Lce<ScreenDataType>?) {
        model = getUpdatedModelForLce(lce)
        refreshView()
    }

    protected abstract suspend fun getFetchScreenData(allowCachedResult: Boolean = false): Flow<Lce<ScreenDataType>>?

    protected abstract fun getUpdatedModelForLce(lce: Lce<ScreenDataType>?): ScreenState

    protected fun <ActionResultType> executeAction(
        actionRequest: suspend () -> Flow<Lce<ActionResultType>>,
        lceUpdateCallback: (Lce<ActionResultType>) -> Unit
    ) {
        lceUpdateCallback(Lce.loading())
        viewModelScope.launch {
            actionRequest()
                .collect {
                    lceUpdateCallback(it)
                }
        }
    }

    override fun reloadScreenData() =
        fetchScreenData()

    open fun onPullToRefresh() =
        reloadScreenData()

    open fun onRetryButtonClicked() =
        reloadScreenData()
}