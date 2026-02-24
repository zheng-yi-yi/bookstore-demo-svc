package com.bookstore.springboot.modules.book.dto;

import com.bookstore.springboot.core.dto.filter.Filter;
import com.bookstore.springboot.core.dto.filter.FilterOperator;
import com.bookstore.springboot.core.dto.result.PagedAndSortedResultRequestDto;
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
