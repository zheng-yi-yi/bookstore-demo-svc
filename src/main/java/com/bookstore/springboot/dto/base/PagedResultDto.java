package com.bookstore.springboot.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResultDto<T> {
    private long totalCount;
    private List<T> items;
}
