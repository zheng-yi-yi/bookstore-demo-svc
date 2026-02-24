package com.bookstore.springboot.core.service;

import com.bookstore.springboot.core.dto.result.PagedResultDto;

public interface ICrudAppService<TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput> {
    TEntityDto create(TCreateInput input);

    TEntityDto update(TKey id, TUpdateInput input);

    TEntityDto get(TKey id);

    void delete(TKey id);

    PagedResultDto<TEntityDto> getList(TGetListInput input);
}
