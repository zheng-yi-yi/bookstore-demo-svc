package com.bookstore.springboot.modules.user.dto;

import com.bookstore.springboot.core.dto.filter.Filter;
import com.bookstore.springboot.core.dto.filter.FilterOperator;
import com.bookstore.springboot.core.dto.result.PagedAndSortedResultRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserGetListInput extends PagedAndSortedResultRequestDto {
    @Filter(operator = FilterOperator.LIKE)
    private String username;

    @Filter(operator = FilterOperator.LIKE)
    private String email;

    @Filter(operator = FilterOperator.LIKE)
    private String name;

    @Filter(operator = FilterOperator.LIKE)
    private String surname;
}
