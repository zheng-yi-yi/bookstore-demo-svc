package com.bookstore.springboot.service.base;

import com.bookstore.springboot.dto.base.PagedResultDto;

public interface ICrudAppService<TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput> {
    TEntityDto create(TCreateInput input);

    TEntityDto update(TKey id, TUpdateInput input);

    TEntityDto get(TKey id);

    void delete(TKey id);

    PagedResultDto<TEntityDto> getList(TGetListInput input);
}
