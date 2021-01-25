package ru.faizovr.afisha.presentation.commands

import ru.faizovr.afisha.presentation.base.BaseCommand
import ru.faizovr.afisha.presentation.model.EventListDataView

sealed class EventListCommands : BaseCommand {
    class OpenEventDetail(val eventListDataView: EventListDataView) : EventListCommands()
}
