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
import ru.faizovr.afisha.presentation.ScreenState
import ru.faizovr.afisha.presentation.fragment.EventDetailFragment
import ru.faizovr.afisha.presentation.mapper.EventDetailInfoDataViewMapper
import ru.faizovr.afisha.presentation.model.EventDetailInfoDataView

class EventDetailViewModel(
    private val repository: Repository,
    bundle: Bundle,
    private val eventDetailInfoDataViewMapper: EventDetailInfoDataViewMapper = EventDetailInfoDataViewMapper()
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private val _eventDetailInfo = MutableLiveData<EventDetailInfoDataView>()
    val eventDetailInfoView: LiveData<EventDetailInfoDataView> = _eventDetailInfo
    private val eventId: Long = bundle.getLong(EventDetailFragment.EVENT_DETAIL_ID_KEY)

    init {
        _screenState.value = ScreenState.Loading
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            val result = repository.getEventDetail(eventId)
            withContext(Dispatchers.Main) {
                if (result is ru.faizovr.afisha.data.wrapper.Result.Success) {
                    _eventDetailInfo.value =
                        eventDetailInfoDataViewMapper.mapFromEntity(result.value)
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