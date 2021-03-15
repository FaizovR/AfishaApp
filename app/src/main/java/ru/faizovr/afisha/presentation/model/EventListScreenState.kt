package ru.faizovr.afisha.presentation.model

import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.core.presentation.models.RefreshableScreenState

class EventListScreenState(
    override val lce: Lce<Nothing>? = null
) : RefreshableScreenState<Nothing>