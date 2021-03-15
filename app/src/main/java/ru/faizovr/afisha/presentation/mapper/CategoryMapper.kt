package ru.faizovr.afisha.presentation.mapper

import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryMapper {
    fun map(item: Category): CategoryDataView =
        CategoryDataView(
            tag = item.tag,
            name = item.categoryName
        )

    fun mapCategories(list: List<Category>?): List<CategoryDataView>? =
        list?.map(this::map)
}