package ru.faizovr.afisha.core.presentation.viewModel

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.faizovr.afisha.core.presentation.navigation.Command
import ru.faizovr.afisha.core.presentation.singleevent.SingleEventLiveData

abstract class BaseViewModel<ScreenState : Any, SupportedCommandType : Command>(
    initialState: ScreenState,
) : ViewModel() {

    protected var model: ScreenState = initialState

    private val modelUpdateEvent = MutableLiveData<ScreenState>()
    private val commandsMutableLiveData: MutableLiveData<SupportedCommandType> =
        SingleEventLiveData()

    val modelUpdate: LiveData<ScreenState> = modelUpdateEvent
    val commandsLiveData: LiveData<SupportedCommandType> = commandsMutableLiveData

    @CallSuper
    open fun onStart() =
        Unit

    open fun onViewResumed() =
        Unit

    open fun onViewPaused() =
        Unit

    protected fun executeCommand(command: SupportedCommandType) {
        commandsMutableLiveData.value = command
    }

    @CallSuper
    protected open fun refreshView() {
        modelUpdateEvent.value = model
    }
}