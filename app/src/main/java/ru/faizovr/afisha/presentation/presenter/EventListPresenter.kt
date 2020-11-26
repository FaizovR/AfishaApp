package ru.faizovr.afisha.presentation.presenter

import ru.faizovr.afisha.data.Repository
import ru.faizovr.afisha.data.remote.callback.EventListCallback
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventList
import ru.faizovr.afisha.presentation.contract.EventListContract

class EventListPresenter(
    private val view: EventListContract.View,
    private val repository: Repository,
    private val category: Category
) : EventListContract.Presenter, EventListCallback {

    override fun init() {
        repository.loadEventListFromApi(this, category.name)
    }

    override fun onEventListLoaded(data: EventList) {
        view.setText(data.events.toString())
    }

    override fun onError() {

    }
}