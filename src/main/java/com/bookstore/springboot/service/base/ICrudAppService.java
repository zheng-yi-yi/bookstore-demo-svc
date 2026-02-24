package com.bookstore.springboot.service.base;

import java.util.List;

public interface ICrudAppService<TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput> {
    TEntityDto create(TCreateInput input);

    TEntityDto update(TKey id, TUpdateInput input);

    TEntityDto get(TKey id);

    void delete(TKey id);

    List<TEntityDto> getList(TGetListInput input);
}
