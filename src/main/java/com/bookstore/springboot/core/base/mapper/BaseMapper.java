package com.bookstore.springboot.core.base.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<TEntity, TEntityDto, TCreateInput, TUpdateInput> {
    TEntityDto toDto(TEntity entity);

    TEntity toEntity(TCreateInput createInput);

    void updateEntityFromDto(TUpdateInput updateInput, @MappingTarget TEntity entity);
}

