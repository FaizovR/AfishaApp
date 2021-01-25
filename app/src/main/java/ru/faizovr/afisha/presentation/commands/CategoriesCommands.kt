package ru.faizovr.afisha.presentation.commands

import ru.faizovr.afisha.presentation.base.BaseCommand
import ru.faizovr.afisha.presentation.model.CategoryDataView

sealed class CategoriesCommands : BaseCommand {
    class OpenEventList(val categoryDataView: CategoryDataView) : CategoriesCommands()
}
