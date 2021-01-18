package ru.faizovr.afisha.presentation.mapper

import ru.faizovr.afisha.data.mapper.EntityMapper
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryDataViewMapper : EntityMapper<Category, CategoryDataView> {
    override fun mapFromEntity(entity: Category): CategoryDataView = CategoryDataView(
        tag = entity.tag,
        name = entity.categoryName
    )
}