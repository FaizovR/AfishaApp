package ru.faizovr.afisha.data.mapper

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
}