package com.bookstore.springboot.modules.book.dto;

import com.bookstore.springboot.core.query.filter.Filter;
import com.bookstore.springboot.core.query.filter.FilterOperator;
import com.bookstore.springboot.core.query.result.PagedAndSortedResultRequestDto;
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

