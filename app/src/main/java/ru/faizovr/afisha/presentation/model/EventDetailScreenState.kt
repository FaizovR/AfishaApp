package ru.faizovr.afisha.presentation.model

import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.core.presentation.models.RefreshableScreenState

class EventDetailScreenState(
    override val lce: Lce<EventDetailDataView>? = null,
    val eventId: Long? = null,
) : RefreshableScreenState<EventDetailDataView> {
    val eventDetail = lce?.content
    val isEventDetailVisible: Boolean = isLoadedSuccessfully()
}