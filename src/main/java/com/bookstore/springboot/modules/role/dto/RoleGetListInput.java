package com.bookstore.springboot.modules.role.dto;

import com.bookstore.springboot.core.dto.filter.Filter;
import com.bookstore.springboot.core.dto.filter.FilterOperator;
import com.bookstore.springboot.core.dto.result.PagedAndSortedResultRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleGetListInput extends PagedAndSortedResultRequestDto {
    @Filter(operator = FilterOperator.LIKE)
    private String name;
}
