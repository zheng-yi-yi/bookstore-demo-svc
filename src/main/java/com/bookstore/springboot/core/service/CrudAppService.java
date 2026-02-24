package com.bookstore.springboot.core.service;

import com.bookstore.springboot.core.dto.filter.Filter;
import com.bookstore.springboot.core.dto.result.OffsetPageRequest;
import com.bookstore.springboot.core.dto.result.PagedAndSortedResultRequestDto;
import com.bookstore.springboot.core.dto.result.PagedResultDto;
import com.bookstore.springboot.core.exception.ResourceNotFoundException;
import com.bookstore.springboot.core.mapper.BaseMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudAppService<TEntity, TEntityDto, TKey, TGetListInput extends PagedAndSortedResultRequestDto, TCreateInput, TUpdateInput> 
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
    public PagedResultDto<TEntityDto> getList(TGetListInput input) {
        Pageable pageable = createPageable(input);
        Specification<TEntity> spec = createFilteredQuery(input);
        
        Page<TEntity> page = ((JpaSpecificationExecutor<TEntity>) repository).findAll(spec, pageable);
        
        return new PagedResultDto<>(
                page.getTotalElements(),
                page.getContent().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    protected Pageable createPageable(TGetListInput input) {
        Sort sort = Sort.unsorted();
        if (StringUtils.hasText(input.getSorting())) {
            // Simple sorting implementation: "fieldName [asc|desc]"
            String[] parts = input.getSorting().split(" ");
            String property = parts[0];
            if (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) {
                sort = Sort.by(property).descending();
            } else {
                sort = Sort.by(property).ascending();
            }
        }
        
        return new OffsetPageRequest(input.getSkipCount(), input.getMaxResultCount(), sort);
    }

    protected Specification<TEntity> createFilteredQuery(TGetListInput input) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Field[] fields = input.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Filter.class)) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(input);
                        if (value == null || (value instanceof String && !StringUtils.hasText((String) value))) {
                            continue;
                        }

                        Filter filter = field.getAnnotation(Filter.class);
                        String propertyName = StringUtils.hasText(filter.property()) ? filter.property() : field.getName();

                        switch (filter.operator()) {
                            case LIKE:
                                predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(propertyName)),
                                    "%" + value.toString().toLowerCase() + "%"
                                ));
                                break;
                            case EQUAL:
                                predicates.add(criteriaBuilder.equal(root.get(propertyName), value));
                                break;
                            case GREATER_THAN:
                                predicates.add(criteriaBuilder.greaterThan(root.get(propertyName), (Comparable) value));
                                break;
                            case LESS_THAN:
                                predicates.add(criteriaBuilder.lessThan(root.get(propertyName), (Comparable) value));
                                break;
                            case GREATER_THAN_OR_EQUAL:
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(propertyName), (Comparable) value));
                                break;
                            case LESS_THAN_OR_EQUAL:
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(propertyName), (Comparable) value));
                                break;
                        }
                    } catch (IllegalAccessException e) {
                        // Log error or handle as needed
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
