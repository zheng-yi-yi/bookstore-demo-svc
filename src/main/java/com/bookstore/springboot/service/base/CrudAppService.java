package com.bookstore.springboot.service.base;

import com.bookstore.springboot.exception.ResourceNotFoundException;
import com.bookstore.springboot.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudAppService<TEntity, TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput> 
    implements ICrudAppService<TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput> {

    @Autowired
    protected JpaRepository<TEntity, TKey> repository;

    @Autowired
    protected BaseMapper<TEntity, TEntityDto, TCreateInput, TUpdateInput> mapper;

    @Override
    public TEntityDto create(TCreateInput input) {
        TEntity entity = mapper.toEntity(input);
        TEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public TEntityDto update(TKey id, TUpdateInput input) {
        TEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
        
        mapper.updateEntityFromDto(input, entity);
        TEntity updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    @Override
    public TEntityDto get(TKey id) {
        TEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public void delete(TKey id) {
        TEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
        repository.delete(entity);
    }

    @Override
    public List<TEntityDto> getList(TGetListInput input) {
        // Default implementation returns all; can be overridden for filtering
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
