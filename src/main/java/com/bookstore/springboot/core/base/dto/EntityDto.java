package com.bookstore.springboot.core.base.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class EntityDto<TKey> {
    private TKey id;
}

