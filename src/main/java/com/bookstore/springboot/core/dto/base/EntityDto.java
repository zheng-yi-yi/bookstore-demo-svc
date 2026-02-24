package com.bookstore.springboot.core.dto.base;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class EntityDto<TKey> {
    private TKey id;
}
