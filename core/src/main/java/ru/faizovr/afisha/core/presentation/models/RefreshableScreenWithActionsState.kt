package ru.faizovr.afisha.core.presentation.models

import ru.faizovr.afisha.core.domain.models.Lce

interface RefreshableScreenWithActionsState<DataType, ActionResultDataType> :
    RefreshableScreenState<DataType> {
    val lceAction: Lce<ActionResultDataType>?

    fun isActionInProgress(): Boolean =
        lceAction?.loading == true

    fun isActionInError(): Boolean =
        lceAction?.error != null

    fun isActionCompletedSuccessfully(): Boolean =
        !isActionInProgress() && !isActionInError() && lceAction?.content != null
}