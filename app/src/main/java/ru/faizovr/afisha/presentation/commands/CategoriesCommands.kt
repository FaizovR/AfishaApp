package ru.faizovr.afisha.presentation.commands

import ru.faizovr.afisha.core.presentation.navigation.Command
import ru.faizovr.afisha.presentation.model.CategoryDataView

sealed class CategoriesCommands : Command {
    class OpenEventList(val categoryDataView: CategoryDataView) : CategoriesCommands()
}
