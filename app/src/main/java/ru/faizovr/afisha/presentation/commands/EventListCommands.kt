package ru.faizovr.afisha.presentation.commands

import ru.faizovr.afisha.core.presentation.navigation.Command
import ru.faizovr.afisha.presentation.model.EventListDataView

sealed class EventListCommands : Command {
    class OpenEventDetail(val eventListDataView: EventListDataView) : EventListCommands()
    object NavigateToPreviousScreen : EventListCommands()
}
