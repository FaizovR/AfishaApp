package ru.faizovr.afisha.core.presentation.viewModel

import androidx.lifecycle.viewModelScope
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
            val lce = getFetchScreenData(allowCachedResult)
            refreshViewForLce(lce)
        }
    }

    private fun refreshViewForLce(lce: Lce<ScreenDataType>?) {
        model = getUpdatedModelForLce(lce)
        refreshView()
    }

    protected abstract suspend fun getFetchScreenData(allowCachedResult: Boolean = false): Lce<ScreenDataType>?

    protected abstract fun getUpdatedModelForLce(lce: Lce<ScreenDataType>?): ScreenState

    protected fun <ActionResultType> executeAction(
        actionRequest: suspend () -> Lce<ActionResultType>,
        lceUpdateCallback: (Lce<ActionResultType>) -> Unit
    ) {
        viewModelScope.launch {
            lceUpdateCallback(Lce.loading())
            val lce = actionRequest()
            lceUpdateCallback(lce)
        }
    }

    override fun reloadScreenData() =
        fetchScreenData()

    open fun onPullToRefresh() =
        reloadScreenData()

    open fun onRetryButtonClicked() =
        reloadScreenData()
}