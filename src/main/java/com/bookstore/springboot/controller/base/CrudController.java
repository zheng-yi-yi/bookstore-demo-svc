package com.bookstore.springboot.controller.base;

import com.bookstore.springboot.service.base.ICrudAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudController<TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput> {

    @Autowired
    protected ICrudAppService<TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TEntityDto create(@RequestBody TCreateInput input) {
        return service.create(input);
    }

    @PutMapping("/{id}")
    public TEntityDto update(@PathVariable TKey id, @RequestBody TUpdateInput input) {
        return service.update(id, input);
    }

    @GetMapping("/{id}")
    public TEntityDto get(@PathVariable TKey id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TKey id) {
        service.delete(id);
    }

    @GetMapping
    public List<TEntityDto> getList(TGetListInput input) {
        return service.getList(input);
    }
}
