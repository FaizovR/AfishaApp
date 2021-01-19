package ru.faizovr.afisha.presentation.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.domain.model.EventDetailInfo
import ru.faizovr.afisha.presentation.fragment.EventDetailFragment
import ru.faizovr.afisha.presentation.mapper.EventDetailInfoDataViewMapper
import ru.faizovr.afisha.presentation.model.EventDetailInfoDataView

class EventDetailViewModel(
    private val repository: Repository,
    bundle: Bundle,
    private val eventDetailInfoDataViewMapper: EventDetailInfoDataViewMapper = EventDetailInfoDataViewMapper()
) : ViewModel() {

    private val eventId: Long = bundle.getLong(EventDetailFragment.EVENT_DETAIL_ID_KEY)

    private val _eventDetailInfo = MutableLiveData<EventDetailInfoDataView>()
    val eventDetailInfoView: LiveData<EventDetailInfoDataView> = _eventDetailInfo

    private val _eventDetailInfoVisibility = MutableLiveData<Boolean>()
    val eventDetailVisibility: LiveData<Boolean> = _eventDetailInfoVisibility

    private val _buttonRetryVisibility = MutableLiveData<Boolean>()
    val buttonRetryVisibility: LiveData<Boolean> = _buttonRetryVisibility

    private val _errorTextVisibility = MutableLiveData<Boolean>()
    val errorTextVisibility: LiveData<Boolean> = _errorTextVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> = _progressBarVisibility


    init {
        loadingState()
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            val result = repository.getEventDetail(eventId)
            withContext(Dispatchers.Main) {
                if (result is ru.faizovr.afisha.data.wrapper.Result.Success) {
                    refreshEventDetailData(result.value)
                    defaultState()
                } else {
                    errorState()
                    Log.e(
                        "TAG",
                        "fetchInfo: ${(result as ru.faizovr.afisha.data.wrapper.Result.Error).exception.localizedMessage} ${
                            result.exception.stackTrace
                        }"
                    )
                }
            }
        }
    }

    private fun refreshEventDetailData(eventDetailInfo: EventDetailInfo) {
        _eventDetailInfo.value = eventDetailInfoDataViewMapper.mapFromEntity(eventDetailInfo)
    }

    fun onRetryClicked() {
        loadingState()
        fetchInfo()
    }

    private fun defaultState() {
        _eventDetailInfoVisibility.value = true
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    private fun loadingState() {
        _eventDetailInfoVisibility.value = false
        _progressBarVisibility.value = true
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    private fun errorState() {
        _eventDetailInfoVisibility.value = false
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = true
        _errorTextVisibility.value = true
    }
}