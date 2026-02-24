package com.bookstore.springboot.dto;

import com.bookstore.springboot.dto.base.Filter;
import com.bookstore.springboot.dto.base.FilterOperator;
import com.bookstore.springboot.dto.base.PagedAndSortedResultRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookGetListInput extends PagedAndSortedResultRequestDto {
    @Filter(operator = FilterOperator.LIKE)
    private String title;

    @Filter(operator = FilterOperator.LIKE)
    private String author;
}
