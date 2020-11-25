package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.CategoriesResponse
import ru.faizovr.afisha.domain.model.Category

class CategoryMapper : EntityMapper<CategoriesResponse, Category> {

    override fun mapFromEntity(entity: CategoriesResponse): Category =
        Category(
            id = entity.id,
            slug = entity.slug,
            name = entity.name
        )

    override fun mapToEntity(domainModel: Category): CategoriesResponse =
        CategoriesResponse(
            id = domainModel.id,
            slug = domainModel.slug,
            name = domainModel.name
        )
}
