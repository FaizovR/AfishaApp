package ru.faizovr.afisha.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.presentation.ScreenState
import ru.faizovr.afisha.presentation.mapper.EventDetailInfoMapper
import ru.faizovr.afisha.presentation.model.EventDetailInfoDataView

class EventDetailViewModel(
    private val repository: Repository,
    private val eventId: Long,
    private val eventDetailInfoMapper: EventDetailInfoMapper = EventDetailInfoMapper()
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private val _eventDetailInfo = MutableLiveData<EventDetailInfoDataView>()
    val eventDetailInfoView: LiveData<EventDetailInfoDataView> = _eventDetailInfo

    init {
        _screenState.value = ScreenState.Loading
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            val result = repository.getEventDetail(eventId)
            withContext(Dispatchers.Main) {
                if (result is ru.faizovr.afisha.data.wrapper.Result.Success) {
                    _eventDetailInfo.value = eventDetailInfoMapper.mapFromEntity(result.value)
                    _screenState.value = ScreenState.Default
                } else {
                    _screenState.value = ScreenState.Error
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
}