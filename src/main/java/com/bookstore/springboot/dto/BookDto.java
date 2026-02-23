package com.bookstore.springboot.dto;

import com.bookstore.springboot.dto.base.AuditedEntityDto;
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
