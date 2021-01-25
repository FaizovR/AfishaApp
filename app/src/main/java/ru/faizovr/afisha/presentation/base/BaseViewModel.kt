package ru.faizovr.afisha.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.faizovr.afisha.presentation.singleEvent.SingleEventLiveData

abstract class BaseViewModel<CommandType : BaseCommand> : ViewModel() {

    private val _buttonRetryVisibility = MutableLiveData<Boolean>()
    val buttonRetryVisibility: LiveData<Boolean> = _buttonRetryVisibility

    private val _errorTextVisibility = MutableLiveData<Boolean>()
    val errorTextVisibility: LiveData<Boolean> = _errorTextVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> = _progressBarVisibility

    private val _commandsEvent: MutableLiveData<CommandType> = SingleEventLiveData()
    val commandsEvent: LiveData<CommandType> = _commandsEvent

    protected fun executeCommand(command: CommandType) {
        _commandsEvent.value = command
    }

    protected open fun setDefaultState() {
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    protected open fun setLoadingState() {
        _progressBarVisibility.value = true
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    protected open fun setErrorState() {
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = true
        _errorTextVisibility.value = true
    }

}