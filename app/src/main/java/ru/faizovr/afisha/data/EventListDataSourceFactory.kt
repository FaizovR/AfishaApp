package ru.faizovr.afisha.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListDataSourceFactory(
    private val repository: Repository,
    private val category: Category
) : DataSource.Factory<String, EventShortInfo>() {

    private val source = MutableLiveData<EventListDataSource>()

    override fun create(): DataSource<String, EventShortInfo> {
        val source = EventListDataSource(repository, category)
        this.source.postValue(source)
        return source
    }
}