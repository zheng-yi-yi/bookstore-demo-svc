package com.bookstore.springboot.modules.user.dto;

import com.bookstore.springboot.core.query.filter.Filter;
import com.bookstore.springboot.core.query.filter.FilterOperator;
import com.bookstore.springboot.core.query.result.PagedAndSortedResultRequestDto;
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

