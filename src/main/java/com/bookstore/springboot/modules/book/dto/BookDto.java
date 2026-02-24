package com.bookstore.springboot.modules.book.dto;

import com.bookstore.springboot.core.dto.base.AuditedEntityDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BookDto extends AuditedEntityDto<UUID> {
    private String title;
    private String author;
    private double price;
}
