package ru.faizovr.afisha.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.EventDetailInfo
import ru.faizovr.afisha.presentation.base.BaseViewModel
import ru.faizovr.afisha.presentation.commands.EventDetailCommands
import ru.faizovr.afisha.presentation.mapper.EventDetailInfoDataViewMapper
import ru.faizovr.afisha.presentation.model.EventDetailInfoDataView

class EventDetailViewModel(
    private val repository: Repository,
    private val eventId: Long,
    private val eventDetailInfoDataViewMapper: EventDetailInfoDataViewMapper = EventDetailInfoDataViewMapper(),
) : BaseViewModel<EventDetailCommands>() {

    private val _eventDetailInfo = MutableLiveData<EventDetailInfoDataView>()
    val eventDetailInfoView: LiveData<EventDetailInfoDataView> = _eventDetailInfo

    private val _eventDetailInfoVisibility = MutableLiveData<Boolean>()
    val eventDetailVisibility: LiveData<Boolean> = _eventDetailInfoVisibility

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        setLoadingState()
        viewModelScope.launch {
            val result = repository.getEventDetail(eventId)
            withContext(Dispatchers.Main) {
                prepareResult(result)
            }
        }
    }

    private fun prepareResult(result: Result<EventDetailInfo>) {
        when (result) {
            is Result.Success -> {
                refreshEventDetailData(result.value)
                setDefaultState()
            }
            is Result.Error -> {
                setErrorState()
                Log.e(
                    "TAG",
                    "fetchInfo: ${result.exception.localizedMessage} ${
                        result.exception.stackTrace
                    }"
                )
            }
        }
    }

    private fun refreshEventDetailData(eventDetailInfo: EventDetailInfo) {
        _eventDetailInfo.value = eventDetailInfoDataViewMapper.mapFromEntity(eventDetailInfo)
    }

    fun onRetryClicked() {
        fetchInfo()
    }

    override fun setDefaultState() {
        super.setDefaultState()
        _eventDetailInfoVisibility.value = true
    }

    override fun setErrorState() {
        super.setErrorState()
        _eventDetailInfoVisibility.value = false
    }

    override fun setLoadingState() {
        super.setLoadingState()
        _eventDetailInfoVisibility.value = false
    }
}