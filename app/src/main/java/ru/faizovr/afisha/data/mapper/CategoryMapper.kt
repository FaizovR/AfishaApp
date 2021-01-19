package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.CategoryResponse
import ru.faizovr.afisha.domain.model.Category

class CategoryMapper : EntityMapper<CategoryResponse, Category> {

    override fun mapFromEntity(entity: CategoryResponse): Category =
        Category(
            id = entity.id,
            tag = entity.slug ?: "",
            categoryName = entity.name ?: ""
        )
}
