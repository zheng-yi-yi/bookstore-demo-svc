package com.bookstore.springboot.core.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PagedAndSortedResultRequestDto {
    private int skipCount = 0;
    private int maxResultCount = 10;
    private String sorting;
}
