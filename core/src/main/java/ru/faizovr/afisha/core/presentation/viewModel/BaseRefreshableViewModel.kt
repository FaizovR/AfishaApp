package ru.faizovr.afisha.core.presentation.viewModel

import androidx.annotation.CallSuper
import ru.faizovr.afisha.core.presentation.models.RefreshableScreen
import ru.faizovr.afisha.core.presentation.models.RefreshableScreenState
import ru.faizovr.afisha.core.presentation.navigation.Command

abstract class BaseRefreshableViewModel<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command>
    (
    initialState: ScreenState
) : BaseViewModel<ScreenState, SupportedCommandType>(initialState), RefreshableScreen {

    @CallSuper
    override fun onViewResumed() {
        super.onViewResumed()
        if (shouldLoadScreenDataOnScreenReopened()) {
            reloadScreenDataOnScreenReopened()
        }
    }

    protected open fun reloadScreenDataOnScreenReopened() =
        reloadScreenData()

    protected open fun shouldLoadScreenDataOnScreenReopened(): Boolean =
        (model.lce?.content == null || model.lce?.error != null) && model.lce?.loading != true
}